package com.jonesyong.module_ximalaya.ui.adapters

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * @author: Jony
 * @date: 2022/5/13
 * @description: 制定一个类似 CardView 的通用的 Adapter
 */
abstract class RecyclerViewAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected val mDataList = mutableListOf<T>()

    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(list: List<T>?) {
        if (list.isNullOrEmpty()) {
            return
        }
        if (mDataList.isNotEmpty()) {
            mDataList.clear()
        }
        mDataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

}