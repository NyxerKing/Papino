package ru.papino.restaurant.core.net.repeater

import android.util.Log
import kotlinx.coroutines.delay

internal class RequestRepeat<T> {
    suspend fun execute(onRequest: () -> T, defaultResponse: T): T {
        var countRequests = 0
        while (countRequests < COUNT_ATTEMPTS) {
            countRequests++

            Log.i(TAG, "attempt request = $countRequests")
            val response = onRequest()
            if (response is RequestRepeatError) {
                delay(SLEEP_TIME)
            } else {
                return response
            }
        }

        return defaultResponse
    }

    companion object {
        private const val TAG = "RequestRepeat"
        private const val SLEEP_TIME = 500L
        private const val COUNT_ATTEMPTS = 3
    }
}