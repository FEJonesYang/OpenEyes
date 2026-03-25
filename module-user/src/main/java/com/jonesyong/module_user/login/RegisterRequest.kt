package com.jonesyong.module_user.login

import com.jonesyong.module_user.api.WanAndroidService

class RegisterRequest(vm: RegisterViewModel) : WanAndroidRequest<RegisterViewModel>(vm) {

    suspend fun register(username: String, password: String, repassword: String) =
        request<WanAndroidService>().register(username, password, repassword)
}
