package com.jonesyong.module_ximalaya.ui.page

import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_base.DataBindingConfig
import com.jonesyong.module_ximalaya.BR
import com.jonesyong.module_ximalaya.R
import com.jonesyong.module_ximalaya.XimalayaViewModel
import com.jonesyong.module_ximalaya.ui.state.HistoryViewModel
import com.ximalaya.ting.android.opensdk.model.history.HistoryModel

class HistoryFragment : BaseFragment(){

    private lateinit var mStateModel: HistoryViewModel


    override fun initViewModel() {
        mStateModel = getFragmentScopeViewModel(HistoryViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_history, BR.vm, mStateModel)
    }

}