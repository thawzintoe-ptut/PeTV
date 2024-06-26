package com.ptut.pmovie.core.network

import arrow.core.Either
import com.ptut.pmovie.core.common.error.DataError
import com.ptut.pmovie.core.model.Movie
import com.ptut.pmovie.core.model.MovieCategory

interface MovieNetworkDataSource {
    suspend fun getMovies(
        movieCategory: MovieCategory,
        page: Int,
    ): Either<DataError.Network, List<Movie>>
}
