package com.ptut.pmovie.core.database

import androidx.paging.PagingData
import com.ptut.pmovie.core.model.Movie
import com.ptut.pmovie.core.model.MovieCategory
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getMovies(movieCategory: MovieCategory): Flow<PagingData<Movie>>

    suspend fun saveMovies(
        movieCategory: MovieCategory,
        movies: List<Movie>,
    )

    suspend fun deleteAllMovies()
}
