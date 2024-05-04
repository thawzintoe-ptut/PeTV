package com.ptut.pmovie.core.network.di

import com.ptut.pmovie.core.network.BuildConfig
import com.ptut.pmovie.core.network.util.NetworkErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule
    @Inject
    constructor() {
        private var instance: HttpClient? = null

        @Provides
        @Singleton
        fun providesNetworkJson(): Json =
            Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            }

        @Provides
        @Singleton
        fun provideKtorClient(
            json: Json,
            networkErrorHandler: NetworkErrorHandler,
        ): HttpClient {
            return instance ?: HttpClient {
                expectSuccess = true
                installTimeoutFeatures()
                installHttpCache()
                installJsonContentNegotiation(json)
                installLogging()
                installResponseObserver()
                installRequestHeader(BuildConfig.ACCESS_TOKEN_KEY)
                installExceptionHandling(networkErrorHandler)
            }
        }

        private fun HttpClientConfig<*>.installTimeoutFeatures() {
            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT
                connectTimeoutMillis = TIMEOUT
                socketTimeoutMillis = TIMEOUT
            }
        }

        private fun HttpClientConfig<*>.installHttpCache() {
            install(HttpCache) {
                val cacheFile =
                    Files.createDirectories(Paths.get("build/cache"))
                        .toFile()
                publicStorage(FileStorage(cacheFile))
            }
        }

        private fun HttpClientConfig<*>.installJsonContentNegotiation(json: Json) {
            install(ContentNegotiation) {
                json(json)
            }
        }

        private fun HttpClientConfig<*>.installLogging() {
            install(Logging) {
                level = LogLevel.ALL
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            Timber.d(message)
                        }
                    }
            }
        }

        private fun HttpClientConfig<*>.installResponseObserver() {
            install(ResponseObserver) {
                onResponse { response ->
                    Timber.d("HTTP status: ${response.status.value}")
                }
            }
        }

        private fun HttpClientConfig<*>.installRequestHeader(token: String) {
            install(DefaultRequest) {
                url(BuildConfig.BACKEND_URL) {
                    parameters.append("language", "en-US")
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                bearerAuth(token)
            }
        }

        private fun HttpClientConfig<*>.installExceptionHandling(networkErrorHandler: NetworkErrorHandler) {
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    when (exception) {
                        is ClientRequestException ->
                            Timber.e(
                                "Client Request Exception: HTTP ${exception.response.status}",
                            )

                        is ServerResponseException ->
                            Timber.e(
                                "Server Response Exception: HTTP ${exception.response.status}",
                            )

                        is ResponseException ->
                            Timber.e(
                                "Response Exception: HTTP ${exception.response.status}",
                            )

                        else ->
                            Timber.e(
                                "Unexpected Exception: ${exception.localizedMessage}",
                            )
                    }
                    networkErrorHandler.handle(exception)
                }
            }
        }

        companion object {
            private const val TIMEOUT = 60_000L
            const val PAGE_KEY = "page"
            const val FAILED_TO_FETCH_MOVIES = "Failed to fetch movies"
        }
    }
