package com.jonesyong.library_base.application

import android.content.Context

interface IApplicationProvider {
    fun onCreate(context: Context)
    fun onTrimMemory(level: Int)
}