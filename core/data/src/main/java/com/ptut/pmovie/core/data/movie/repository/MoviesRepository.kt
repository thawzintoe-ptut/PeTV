package com.ptut.pmovie.core.data.movie.repository

import androidx.paging.PagingData
import com.ptut.pmovie.core.model.Movie
import com.ptut.pmovie.core.model.MovieCategory
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(movieCategory: MovieCategory): Flow<PagingData<Movie>>

    suspend fun fetchMovies(movieCategory: MovieCategory)
}
