package com.jonesyong.module_ximalaya.api.request

import com.jonesyong.library_base.model.BaseRequest
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.module_ximalaya.util.XiMALAYA_BASE_URL

open class XimalayaCommonRequest(viewModel: BaseViewModel) :
    BaseRequest<BaseViewModel>(viewModel) {
    override var url: String = XiMALAYA_BASE_URL
}