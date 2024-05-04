package com.ptut.pmovie.core.network.util

import com.ptut.pmovie.core.common.error.DataError
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import java.util.concurrent.TimeoutException

object NetworkErrorHandler {
    fun handle(throwable: Throwable): DataError.Network =
        when (throwable) {
            is ClientRequestException -> mapHttpError(throwable.response.status.value)
            is ServerResponseException -> DataError.Network.INTERNAL_SERVER_ERROR
            is ResponseException -> DataError.Network.BAD_GATEWAY
            is TimeoutException -> DataError.Network.REQUEST_TIME_OUT
            is IOException -> DataError.Network.NETWORK_UNAVAILABLE
            else -> DataError.Network.UNKNOWN
        }

    private fun mapHttpError(statusCode: Int): DataError.Network =
        when (statusCode) {
            400 -> DataError.Network.BAD_REQUEST
            404 -> DataError.Network.NOT_FOUND
            408 -> DataError.Network.REQUEST_TIME_OUT
            500 -> DataError.Network.INTERNAL_SERVER_ERROR
            502 -> DataError.Network.BAD_REQUEST
            503 -> DataError.Network.SERVICE_UNAVAILABLE
            403 -> DataError.Network.FORBIDDEN
            401 -> DataError.Network.UNAUTHORIZED
            else -> DataError.Network.UNKNOWN
        }
}
