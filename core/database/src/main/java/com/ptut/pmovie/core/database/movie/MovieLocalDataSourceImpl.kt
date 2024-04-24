package com.ptut.pmovie.core.database.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.ptut.pmovie.core.database.MovieLocalDataSource
import com.ptut.pmovie.core.database.PeTVDatabase
import com.ptut.pmovie.core.database.movie.dao.MoviesDao
import com.ptut.pmovie.core.database.movie.entity.MovieCategoryEntity
import com.ptut.pmovie.core.database.movie.entity.MovieEntity
import com.ptut.pmovie.core.database.util.asEntity
import com.ptut.pmovie.core.database.util.asModel
import com.ptut.pmovie.core.model.Movie
import com.ptut.pmovie.core.model.MovieCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalDataSourceImpl
    @Inject
    constructor(
        private val db: PeTVDatabase,
        private val moviesDao: MoviesDao,
    ) : MovieLocalDataSource {
        override fun getMovies(movieCategory: MovieCategory): Flow<PagingData<Movie>> {
            return Pager(
                config =
                    PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = false,
                    ),
                pagingSourceFactory = {
                    moviesDao.getMoviesByCategory(movieCategory.type)
                },
            ).flow.map { pagingData ->
                pagingData.map(MovieEntity::asModel)
            }
        }

        override suspend fun saveMovies(
            movieCategory: MovieCategory,
            movies: List<Movie>,
        ) {
            val movieCategories =
                movies.map { movie ->
                    MovieCategoryEntity(movieId = movie.id, categoryName = movieCategory.type)
                }

            db.withTransaction {
                moviesDao.insertAllMovie(movies.map(Movie::asEntity))
                moviesDao.insertAllMovieCategory(movieCategories)
            }
        }

        override suspend fun deleteAllMovies() {
            db.withTransaction {
                moviesDao.deleteAllData()
            }
        }
    }
