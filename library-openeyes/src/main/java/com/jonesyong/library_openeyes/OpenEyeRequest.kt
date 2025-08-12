package com.jonesyong.library_openeyes

import com.jonesyong.library_base.model.BaseRequest
import com.jonesyong.library_base.model.BaseViewModel

open class OpenEyeRequest(viewModel: BaseViewModel) : BaseRequest<BaseViewModel>(viewModel) {
    override var url: String = "https://baobab.kaiyanapp.com"
}