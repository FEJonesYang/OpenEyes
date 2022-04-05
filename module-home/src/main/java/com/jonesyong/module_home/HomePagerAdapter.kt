package com.jonesyong.module_home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @Author JonesYang
 * @Date 2022-02-11
 * @Description 首页 ViewPager 的 Adapter
 */

class HomePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return Fragment()
    }

    override fun getItemCount(): Int {
        return 0
    }
}