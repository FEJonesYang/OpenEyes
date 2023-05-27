package com.jonesyong.module_ximalaya.ui.page

import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_base.DataBindingConfig
import com.jonesyong.module_ximalaya.BR
import com.jonesyong.module_ximalaya.R
import com.jonesyong.module_ximalaya.ui.state.HistoryViewModel
import com.jonesyong.module_ximalaya.ui.state.SubscriptionViewModel

class SubscriptionFragment : BaseFragment(){

    private lateinit var mStateModel: SubscriptionViewModel


    override fun initViewModel() {
        mStateModel = getFragmentScopeViewModel(SubscriptionViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_subscription, BR.vm, mStateModel)
    }
}