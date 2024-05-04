package com.ptut.pmovie.core.network.util

import android.util.Log
import io.mockk.clearAllMocks
import io.mockk.mockkStatic
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class AddLogExt : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        println("clearing mocks...")
        clearAllMocks()
        println("mocking Log...")
        val timber = mockkStatic(Log::class)
    }
}
