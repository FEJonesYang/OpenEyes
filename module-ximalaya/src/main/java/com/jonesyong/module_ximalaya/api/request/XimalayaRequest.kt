package com.jonesyong.module_ximalaya.api.request

import com.jonesyong.module_ximalaya.XimalayaViewModel
import com.jonesyong.module_ximalaya.api.XimalayaService

class XimalayaRequest(viewModel: XimalayaViewModel) : XimalayaCommonRequest(viewModel) {

    suspend fun loadCategories() =
        request<XimalayaService>().fetchCategories()
}