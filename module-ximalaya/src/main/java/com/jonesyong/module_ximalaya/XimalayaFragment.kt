package com.jonesyong.module_ximalaya

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_foundation.util.view.AppCompatErrorView

class XimalayaFragment : BaseFragment<XimalayaViewModel>() {

    private var adapter: XimalayaFragmentPageAdapter? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager2? = null

    override fun initViewModel() {
        super.initViewModel()
        vm = getFragmentScopeViewModel(XimalayaViewModel::class.java)
        vm.loadCategories()
    }

    override fun initView(root: View) {
        super.initView(root)
        adapter = XimalayaFragmentPageAdapter(this)
        tabLayout = root.findViewById(R.id.tab_ximalaya)
        viewPager = root.findViewById(R.id.viewPager_ximalaya)
        viewPager?.adapter = adapter
    }

    override fun onSubscribeUi(view: View) {
        super.onSubscribeUi(view)
        vm.tabList.observe(viewLifecycleOwner) {
            adapter?.setData(it)

            // 关联 ViewPager 和 TabLayout
            TabLayoutMediator(
                tabLayout!!,
                viewPager!!
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

    override fun getInflateId(): Int = R.layout.fragment_ximalaya
}