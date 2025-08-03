package com.jonesyong.library_foundation.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

object ExecutorManager {

    private val mainHandler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    fun runOnUi(time: Long = 0, block: () -> Unit) {
        if (Thread.currentThread() == mainHandler.looper.thread) {
            block()
            return
        }
        mainHandler.postDelayed({
            block()
        }, time)
    }

    fun runOnBg(block: () -> Unit) {
        executor.execute {
            block()
        }
    }
}