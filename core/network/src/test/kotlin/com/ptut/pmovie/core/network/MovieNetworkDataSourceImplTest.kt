package com.ptut.pmovie.core.network

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.ptut.pmovie.core.model.MovieCategory
import com.ptut.pmovie.core.network.ktor.MOVIE_EMPTY_JSON
import com.ptut.pmovie.core.network.ktor.MOVIE_ERROR_JSON
import com.ptut.pmovie.core.network.ktor.createClient
import com.ptut.pmovie.core.network.ktor.errorKtorClient
import com.ptut.pmovie.core.network.ktor.successKtorClient
import com.ptut.pmovie.core.network.util.AddLogExt
import io.ktor.client.HttpClient
import io.ktor.http.HttpStatusCode
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AddLogExt::class)
class MovieNetworkDataSourceImplTest {
    private lateinit var movieDataSource: MovieNetworkDataSource
    private lateinit var ktorClient: HttpClient

    @Test
    @DisplayName("success movies when fetching")
    fun getMovies_ReturnsListOfMovies_OnSuccess() =
        runBlocking {
            // Arrange
            movieDataSource = spyk<MovieNetworkDataSource>()
            movieDataSource =
                MovieNetworkDataSourceImpl(
                    ktorClient = successKtorClient,
                )

            // Act
            val result =
                movieDataSource.getMovies(
                    movieCategory = MovieCategory.POPULAR,
                    page = 1,
                )
            // Assert
            assertThat(result.isRight()).isTrue()
            assertThat(result.getOrNull()?.size).isEqualTo(2)
        }

    @Test
    @DisplayName("error movies when fetching")
    fun getMovies_ReturnsNetworkError_WhenApiResponseIsServerError() {
        runBlocking {
            // Arrange
            movieDataSource = spyk<MovieNetworkDataSource>()
            movieDataSource =
                MovieNetworkDataSourceImpl(
                    ktorClient = errorKtorClient,
                )

            // Act
            val result =
                movieDataSource.getMovies(
                    movieCategory = MovieCategory.POPULAR,
                    page = 1,
                )

            // Assert
            assertThat(result.isLeft()).isTrue()
        }
    }

    @Test
    @DisplayName("empty movies when fetching")
    fun getMovies_ReturnsEmptyList_WhenApiResponseIsEmpty() {
        runBlocking {
            // Arrange
            val emptyKtorClient =
                createClient(
                    MOVIE_EMPTY_JSON,
                    HttpStatusCode.OK,
                )
            movieDataSource = spyk<MovieNetworkDataSource>()
            movieDataSource =
                MovieNetworkDataSourceImpl(
                    ktorClient = emptyKtorClient,
                )

            // Act
            val result =
                movieDataSource.getMovies(
                    movieCategory = MovieCategory.POPULAR,
                    page = 2,
                )

            // Assert
            assertThat(result.isRight()).isTrue()
            assertThat(result.getOrNull()?.size).isEqualTo(0)
        }
    }

    @Test
    @DisplayName("timeout error when fetching movies")
    fun getMovies_ReturnsNetworkError_WhenRequestTimesOut() {
        runBlocking {
            // Arrange
            val timeoutKtorClient =
                createClient(
                    MOVIE_ERROR_JSON,
                    HttpStatusCode.RequestTimeout,
                )
            movieDataSource = spyk<MovieNetworkDataSource>()
            movieDataSource =
                MovieNetworkDataSourceImpl(
                    ktorClient = timeoutKtorClient,
                )

            // Act
            val result =
                movieDataSource.getMovies(
                    movieCategory = MovieCategory.POPULAR,
                    page = 2,
                )

            // Assert
            assertThat(result.isLeft()).isTrue()
        }
    }

    @Test
    @DisplayName("malformed json fetching movies")
    fun getMovies_ReturnsError_WhenJsonIsMalformed() {
        val malformedJson = """{"results":[{"title":"Inception","description":}]}"""
        runBlocking {
            // Arrange
            val malformedKtorClient =
                createClient(
                    malformedJson,
                    HttpStatusCode.OK,
                )
            movieDataSource = spyk<MovieNetworkDataSource>()
            movieDataSource =
                MovieNetworkDataSourceImpl(
                    ktorClient = malformedKtorClient,
                )

            // Act
            val result =
                movieDataSource.getMovies(
                    movieCategory = MovieCategory.POPULAR,
                    page = 2,
                )

            // Assert
            assertThat(result.isLeft()).isTrue()
        }
    }
}
