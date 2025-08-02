package com.jonesyong.library_common

import com.jonesyong.library_base.application.ApplicationProvider

const val scheme = ".*"
const val host = ".*"

val applicationContext by lazy { ApplicationProvider.getContext() }
