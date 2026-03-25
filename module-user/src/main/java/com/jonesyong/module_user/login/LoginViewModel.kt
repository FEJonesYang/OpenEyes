package com.jonesyong.module_user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonesyong.library_base.ktx.safeLaunch
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_user.api.UserInfo
import com.jonesyong.module_user.store.UserStore
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    val loginSuccess = MutableLiveData<UserInfo>()
    val loginError = MutableLiveData<String>()

    private val request = LoginRequest(this)

    override fun loadData() {}

    fun login(username: String, password: String) {
        viewModelScope.launch {
            beforeRequest()
            val response = safeLaunch { request.login(username, password) }
            afterRequest(response != null) {
                if (response!!.code == 0) {
                    UserStore.saveUser(username)
                    loginSuccess.value = response.data
                } else {
                    loginError.value = response.message
                }
            }
        }
    }
}
