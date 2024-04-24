package com.ptut.pmovie.core.data.movie

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ptut.pmovie.core.database.MovieLocalDataSource
import com.ptut.pmovie.core.database.RemoteKeyLocalDataSource
import com.ptut.pmovie.core.database.util.RemoteKeys
import com.ptut.pmovie.core.model.Movie
import com.ptut.pmovie.core.model.MovieCategory
import com.ptut.pmovie.core.network.MovieNetworkDataSource
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PopularMoviesRemoteMediator
    @Inject
    constructor(
        private val movieNetworkDataSource: MovieNetworkDataSource,
        private val movieLocalDataSource: MovieLocalDataSource,
        private val remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    ) : RemoteMediator<Int, Movie>() {
        override suspend fun initialize(): InitializeAction {
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        }

        @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, Movie>,
        ): MediatorResult {
            val page =
                when (loadType) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                        val prevKey =
                            remoteKeys?.prevKey
                                ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                        prevKey
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        val nextKey =
                            remoteKeys?.nextKey
                                ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                        nextKey
                    }
                }
            try {
                val apiResponse =
                    movieNetworkDataSource.getMovies(
                        movieCategory = MovieCategory.POPULAR,
                        page = page,
                    )
                val movieDtos = apiResponse.getOrNull() ?: emptyList()
                val endOfPaginationReached = movieDtos.isEmpty()
                if (loadType == LoadType.REFRESH) {
                    remoteKeyLocalDataSource.deleteAllRemoteKeys()
                    movieLocalDataSource.deleteAllMovies()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys =
                    movieDtos.map {
                        RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                remoteKeyLocalDataSource.saveRemoteKeys(keys)
                movieLocalDataSource.saveMovies(MovieCategory.POPULAR, movieDtos)
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (exception: IOException) {
                return MediatorResult.Error(exception)
            } catch (exception: HttpException) {
                return MediatorResult.Error(exception)
            } catch (e: Exception) {
                return MediatorResult.Error(e)
            }
        }

        private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): RemoteKeys? {
            return state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { repoId ->
                    remoteKeyLocalDataSource.getRemoteKeysByMovieId(repoId)
                }
            }
        }

        private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): RemoteKeys? {
            return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                ?.let { repo ->
                    remoteKeyLocalDataSource.getRemoteKeysByMovieId(repo.id)
                }
        }

        private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): RemoteKeys? {
            return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { repo ->
                    remoteKeyLocalDataSource.getRemoteKeysByMovieId(repo.id)
                }
        }
    }
