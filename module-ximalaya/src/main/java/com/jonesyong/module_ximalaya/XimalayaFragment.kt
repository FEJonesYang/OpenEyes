package com.jonesyong.module_ximalaya

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_base.DataBindingConfig
import com.jonesyong.library_foundation.util.view.AppCompatErrorView
import com.jonesyong.module_ximalaya.databinding.FragmentXimalayaBinding

class XimalayaFragment : BaseFragment<XimalayaViewModel>() {

    private lateinit var mDataBinding: FragmentXimalayaBinding
    private var adapter: XimalayaFragmentPageAdapter? = null

    override fun initViewModel() {
        vm = getFragmentScopeViewModel(XimalayaViewModel::class.java)
        vm.loadCategories()
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_ximalaya, BR.vm, vm)
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
        vm.tabList.observe(viewLifecycleOwner) {
            adapter?.setData(it)

            // 关联 ViewPager 和 TabLayout
            TabLayoutMediator(
                mDataBinding.tabXimalaya,
                mDataBinding.viewPagerXimalaya
            ) { tab, position ->
                tab.text = vm.tabList.value?.get(position)?.category_name
            }.attach()
        }
    }

    override fun inflateErrorView(view: View): AppCompatErrorView? =
        view.findViewById<AppCompatErrorView?>(R.id.network_error).apply {
            setTryAgainListener {
                vm.loadCategories()
            }
        }
}