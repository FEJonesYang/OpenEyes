package com.jonesyong.module_ximalaya

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_base.DataBindingConfig
import com.jonesyong.module_ximalaya.databinding.FragmentXimalayaBinding
import com.jonesyong.module_ximalaya.ui.adapters.XimalayaFragmentPageAdapter

class XimalayaFragment : BaseFragment() {

    private lateinit var mStateModel: XimalayaViewModel
    private lateinit var mDataBinding: FragmentXimalayaBinding

    override fun initViewModel() {
        mStateModel = getFragmentScopeViewModel(XimalayaViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_ximalaya, BR.vm, mStateModel)
            .addBindingParam(
                BR.adapter,
                XimalayaFragmentPageAdapter(parentFragmentManager, lifecycle)
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDataBinding = mBinding as FragmentXimalayaBinding
        mDataBinding.viewPagerXimalaya.adapter =
            mBindingConfig.bindingParams.get(BR.adapter) as XimalayaFragmentPageAdapter

        // 关联 ViewPager 和 TabLayout
        TabLayoutMediator(
            mDataBinding.tabXimalaya,
            mDataBinding.viewPagerXimalaya
        ) { tab, position ->
            tab.text = mStateModel.tabList[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}