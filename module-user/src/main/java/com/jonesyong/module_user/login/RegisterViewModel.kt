package com.jonesyong.module_user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonesyong.library_base.ktx.safeLaunch
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_user.api.UserInfo
import com.jonesyong.module_user.store.UserStore
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel() {

    val registerSuccess = MutableLiveData<UserInfo>()
    val registerError = MutableLiveData<String>()

    private val request = RegisterRequest(this)

    override fun loadData() {}

    fun register(username: String, password: String, repassword: String) {
        viewModelScope.launch {
            beforeRequest()
            val response = safeLaunch { request.register(username, password, repassword) }
            afterRequest(response != null) {
                if (response!!.code == 0) {
                    UserStore.saveUser(username)
                    registerSuccess.value = response.data
                } else {
                    registerError.value = response.message
                }
            }
        }
    }
}
