package com.ptut.pmovie.core.network

import arrow.core.Either
import com.ptut.pmovie.core.common.catchIO
import com.ptut.pmovie.core.model.Movie
import com.ptut.pmovie.core.model.MovieCategory
import com.ptut.pmovie.core.model.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class MovieNetworkDataSourceImpl
    @Inject
    constructor(
        private val ktorClient: HttpClient,
    ) : MovieNetworkDataSource {
        companion object {
            private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        }

        override suspend fun getMovies(
            movieCategory: MovieCategory,
            page: Int,
        ): Either<Throwable, List<Movie>> =
            catchIO {
                val response =
                    ktorClient.get("${BASE_URL}${movieCategory.type}") {
                        parameter("language", "en")
                        parameter("page", page)
                    }
                when (response.status.value) {
                    in 200..299 -> response.body<MoviesResponse>().results
                    else -> throw Exception("Error fetching movies")
                }
            }
    }
