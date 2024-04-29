package com.ptut.pmovie.core.common

import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <A, B> catchIO(
    block: suspend () -> A,
    errorBlock: suspend (t: Throwable) -> B,
): Either<B, A> =
    Either.catch {
        withContext(Dispatchers.IO) {
            block()
        }
    }.mapLeft {
        errorBlock(it)
    }
