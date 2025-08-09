package com.jonesyong.module_ximalaya

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jonesyong.module_ximalaya.api.data.Categories
import com.jonesyong.module_ximalaya.ui.page.RecommendFragment


/**
 * @author: Jony
 * @date: 2022/5/11
 * @description:
 */
class XimalayaFragmentPageAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {


    private val dataList = mutableListOf<Categories>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Categories>?) {
        if (data.isNullOrEmpty()) {
            return
        }
        if (dataList.isNotEmpty()) {
            dataList.clear()
        }
        dataList.addAll(data)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = dataList.size

    override fun createFragment(position: Int): Fragment =
        RecommendFragment.newInstance(dataList[position].id)
}