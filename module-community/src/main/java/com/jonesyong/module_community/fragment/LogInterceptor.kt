package com.jonesyong.module_community.fragment

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

class LogInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor
    override fun <T> interceptContinuation(continuation: Continuation<T>)
            = LogContinuation(continuation)
}

class LogContinuation<T>(private val continuation: Continuation<T>)
    : Continuation<T> by continuation {
    override fun resumeWith(result: Result<T>) {
        println("before resumeWith: $result")
        continuation.resumeWith(result)
        println("after resumeWith.")
    }
}