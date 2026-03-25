package com.jonesyong.module_user

import androidx.lifecycle.MutableLiveData
import com.jonesyong.library_base.model.BaseViewModel

class UserViewModel : BaseViewModel() {

    val username = MutableLiveData<String?>()

    override fun loadData() {}
}
