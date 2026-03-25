package com.jonesyong.module_user.login

import com.didi.drouter.annotation.Router
import com.jonesyong.library_base.BaseActivity
import com.jonesyong.module_user.R

@Router(path = "/user/login")
class LoginActivity : BaseActivity<ContainerViewModel>() {

    override fun getInflateId() = R.layout.activity_login

    override fun initViewModel() {
        vm = getActivityScopeViewModel(ContainerViewModel::class.java)
    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment())
            .commit()
    }
}
