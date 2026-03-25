package com.jonesyong.module_user.login

import com.jonesyong.library_base.model.BaseRequest
import com.jonesyong.library_base.model.BaseViewModel

abstract class WanAndroidRequest<VM : BaseViewModel>(vm: VM) : BaseRequest<VM>(vm) {
    override var url = "https://www.wanandroid.com/"
}
