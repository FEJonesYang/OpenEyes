package com.jonesyong.module_ximalaya

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_base.DataBindingConfig
import com.jonesyong.module_ximalaya.databinding.FragmentXimalayaBinding

class XimalayaFragment : BaseFragment() {

    private lateinit var mStateModel: XimalayaViewModel
    private lateinit var mDataBinding: FragmentXimalayaBinding
    private var adapter: XimalayaFragmentPageAdapter? = null

    override fun initViewModel() {
        mStateModel = getFragmentScopeViewModel(XimalayaViewModel::class.java)
        mStateModel.loadCategories()
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
        adapter = mBindingConfig.bindingParams.get(BR.adapter) as XimalayaFragmentPageAdapter
        mDataBinding.viewPagerXimalaya.adapter = adapter
    }

    override fun onSubscribeUi(view: View) {
        super.onSubscribeUi(view)
        mStateModel.tabList.observe(viewLifecycleOwner) {
            adapter?.setData(it)

            // 关联 ViewPager 和 TabLayout
            TabLayoutMediator(
                mDataBinding.tabXimalaya,
                mDataBinding.viewPagerXimalaya
            ) { tab, position ->
                tab.text = mStateModel.tabList.value?.get(position)?.category_name
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}