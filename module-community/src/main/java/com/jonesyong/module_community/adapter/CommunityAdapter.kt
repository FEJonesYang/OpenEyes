package com.jonesyong.module_community.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jonesyong.module_community.CommunityFragment
import com.jonesyong.module_community.PageData
import com.jonesyong.module_community.fragment.FollowFragment
import com.jonesyong.module_community.fragment.RecommendFragment

class CommunityAdapter(fragment: CommunityFragment) : FragmentStateAdapter(fragment) {

    private val pageList = mutableListOf<PageData>()

    override fun getItemCount(): Int = pageList.size

    override fun createFragment(position: Int): Fragment = when (pageList[position].type) {
        "recommend" -> RecommendFragment.newInstance()
        "follow" -> FollowFragment.newInstance()
        else -> Fragment()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(pageDataList: MutableList<PageData>?) {
        if (pageDataList.isNullOrEmpty()) {
            return
        }
        pageList.clear()
        pageList.addAll(pageDataList)
        notifyDataSetChanged()
    }
}