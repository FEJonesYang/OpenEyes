package com.jonesyong.library_common

import com.jonesyong.library_base.application.ApplicationProvider

const val scheme = ".*"
const val host = ".*"

const val ROUTE_USER_LOGIN = "/user/login"
const val ROUTE_USER_REGISTER = "/user/register"

val applicationContext by lazy { ApplicationProvider.getContext() }
