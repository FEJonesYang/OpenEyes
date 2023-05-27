package com.jonesyong.module_ximalaya.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jonesyong.module_ximalaya.ui.page.HistoryFragment
import com.jonesyong.module_ximalaya.ui.page.RecommendFragment
import com.jonesyong.module_ximalaya.ui.page.SubscriptionFragment


/**
 * @author: Jony
 * @date: 2022/5/11
 * @description:
 */
class XimalayaFragmentPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val list  = mutableListOf<Fragment>()

    init {
        list.add(RecommendFragment())
        list.add(HistoryFragment())
        list.add(SubscriptionFragment())
    }

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]

}