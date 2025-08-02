package com.jonesyong.library_base.ktx

import android.util.Log
import com.jonesyong.library_base.ktx.CoroutineScopeHandler.exceptionHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.EmptyCoroutineContext

object CoroutineScopeHandler {

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("CoroutineScopeHandler", exception.printStackTrace().toString())
    }
}

suspend fun <T> CoroutineScope.safeLaunch(
    fail: ((Throwable) -> Unit)? = null,
    block: (suspend CoroutineScope.() -> T)
): T? {
    return async(EmptyCoroutineContext + exceptionHandler) {
        try {
            block()
        } catch (e: Throwable) {
            fail?.invoke(e)
            null
        }
    }.await()
}