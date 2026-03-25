package com.jonesyong.module_user.login

import com.jonesyong.module_user.api.WanAndroidService

class LoginRequest(vm: LoginViewModel) : WanAndroidRequest<LoginViewModel>(vm) {

    suspend fun login(username: String, password: String) =
        request<WanAndroidService>().login(username, password)
}
