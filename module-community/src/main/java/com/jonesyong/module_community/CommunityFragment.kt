package com.jonesyong.module_community

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.module_community.adapter.CommunityAdapter


/**
 * @Author JonesYang
 * @Date 2022-02-07
 * @Description
 */
class CommunityFragment : BaseFragment<CommunityViewModel>() {

    private var mTabLayout: TabLayout? = null
    private var mViewPager: ViewPager2? = null
    private var mAdapter: CommunityAdapter? = null

    override fun getInflateId(): Int = R.layout.fragment_community

    override fun initViewModel() {
        super.initViewModel()
        vm = getFragmentScopeViewModel(CommunityViewModel::class.java)
        vm.fetchPageList()
    }

    override fun initView(root: View) {
        super.initView(root)
        mTabLayout = root.findViewById(R.id.community_tab)
        mViewPager = root.findViewById(R.id.community_fragment_container)
        mAdapter = CommunityAdapter(this)
        mViewPager?.adapter = mAdapter
    }

    override fun onSubscribeUi(view: View) {
        super.onSubscribeUi(view)
        vm.pageList.observe(viewLifecycleOwner) { pageList ->
            mAdapter?.setData(pageList)
            TabLayoutMediator(
                mTabLayout!!, mViewPager!!
            ) { tab: TabLayout.Tab, position: Int ->
                tab.setText(pageList[position].name)
            }.attach()
        }
    }
}