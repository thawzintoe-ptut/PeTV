package com.ptut.pmovie.core.network.ktor

import com.ptut.pmovie.core.network.service.TMDBApiRoutes
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private val responseHeaders = headersOf(HttpHeaders.ContentType, "application/json")

val successKtorClient =
    HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    "${TMDBApiRoutes.MOVIES}/popular" ->
                        respond(
                            content = MOVIE_SUCCESS_JSON,
                            status = HttpStatusCode.OK,
                            headers = responseHeaders,
                        )

                    "${TMDBApiRoutes.MOVIES}/top_rated" ->
                        respond(
                            content = MOVIE_SUCCESS_JSON,
                            status = HttpStatusCode.OK,
                            headers = responseHeaders,
                        )

                    "${TMDBApiRoutes.MOVIES}/upcoming" ->
                        respond(
                            content = MOVIE_SUCCESS_JSON,
                            status = HttpStatusCode.OK,
                            headers = responseHeaders,
                        )

                    "${TMDBApiRoutes.MOVIES}/now_playing" ->
                        respond(
                            content = MOVIE_SUCCESS_JSON,
                            status = HttpStatusCode.OK,
                            headers = responseHeaders,
                        )

                    else -> error("Unhandled ${request.url.encodedPath}")
                }
            }
        }
        install(Logging) {
            logger =
                object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                },
            )
        }
    }

val errorKtorClient =
    HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    TMDBApiRoutes.MOVIES ->
                        respond(
                            content = MOVIE_ERROR_JSON,
                            status = HttpStatusCode.BadRequest,
                            headers = responseHeaders,
                        )

                    else -> error("Unhandled ${request.url.encodedPath}")
                }
            }
        }
        expectSuccess = true
        install(Logging) {
            logger =
                object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                },
            )
        }
    }

/**
 * Need more flexibility? No problem! In this dynamic client, I pass the JSON
 * response and the HttpStatus code that I'd like to use.
 */
fun createClient(
    json: String,
    status: HttpStatusCode,
) = HttpClient(MockEngine) {
    engine {
        addHandler {
            respond(json, status, responseHeaders)
        }
    }
    expectSuccess = true
    install(Logging) {
        logger =
            object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            },
        )
    }
}
