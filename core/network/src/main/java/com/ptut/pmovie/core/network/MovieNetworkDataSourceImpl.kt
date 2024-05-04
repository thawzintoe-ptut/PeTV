package com.ptut.pmovie.core.network

import arrow.core.Either
import com.ptut.pmovie.core.common.catchIO
import com.ptut.pmovie.core.common.error.DataError
import com.ptut.pmovie.core.model.Movie
import com.ptut.pmovie.core.model.MovieCategory
import com.ptut.pmovie.core.model.MoviesResponse
import com.ptut.pmovie.core.network.di.NetworkModule.Companion.FAILED_TO_FETCH_MOVIES
import com.ptut.pmovie.core.network.di.NetworkModule.Companion.PAGE_KEY
import com.ptut.pmovie.core.network.service.TMDBApiRoutes.MOVIES
import com.ptut.pmovie.core.network.util.NetworkErrorHandler
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import javax.inject.Inject

class MovieNetworkDataSourceImpl
    @Inject
    constructor(
        private val ktorClient: HttpClient,
    ) : MovieNetworkDataSource {
        override suspend fun getMovies(
            movieCategory: MovieCategory,
            page: Int,
        ): Either<DataError.Network, List<Movie>> =
            catchIO(
                block = { fetchMovies(movieCategory, page) },
                errorBlock = { NetworkErrorHandler.handle(it) },
            )

        private suspend fun fetchMovies(
            movieCategory: MovieCategory,
            page: Int,
        ): List<Movie> {
            val response: HttpResponse =
                ktorClient.get("$MOVIES/${movieCategory.type}") {
                    parameter(PAGE_KEY, page)
                }
            if (response.status.isSuccess()) {
                return response.body<MoviesResponse>().results
            } else {
                throw ClientRequestException(response, FAILED_TO_FETCH_MOVIES)
            }
        }
    }
