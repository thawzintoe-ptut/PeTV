package com.ptut.pmovie.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
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
    @Provides
    @Singleton
    fun providesNetworkJson(): Json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    @Provides
    @Singleton
    fun provideKtorClient(json: Json): HttpClient {
        return HttpClient {
            installTimeoutFeatures()
            installHttpCache()
            installJsonContentNegotiation(json)
            installLogging()
            installResponseObserver()
            installDefaultRequest(AUTH_TOKEN)
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
            val cacheFile = Files.createDirectories(Paths.get("build/cache")).toFile()
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
            level = LogLevel.BODY
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

    private fun HttpClientConfig<*>.installDefaultRequest(token: String) {
        install(DefaultRequest) {
            url("https://api.themoviedb.org/3/")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            bearerAuth(token)
        }
    }

    companion object {
        private const val TIMEOUT = 60_000L
        private const val AUTH_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjcwYjFhNjM1ZDI3OWY3NzhhOWM0ZTI0ZWU0YWM3YSIsInN1YiI6IjU5YjBjMTg5YzNhMzY4MmMyMjA3Nzc3YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.90Ws_Kc6y67vyc_GB14POzSLL5Pje2PFrK70sA6GwE0"
    }
}
