package com.ptut.pmovie.core.network

import androidx.paging.PagingData
import com.ptut.pmovie.core.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieNetworkDataSource {
    fun getTrendingMoviesThisWeek(): Flow<PagingData<Movie>>

    fun getUpcomingMovies(): Flow<PagingData<Movie>>

    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    fun getNowPlayingMovies(): Flow<PagingData<Movie>>

    fun getPopularMovies(): Flow<PagingData<Movie>>
}
