package com.jonesyong.library_common.content

import android.content.Context
import android.view.View
import android.view.ViewGroup

abstract class HolderFactory<T, Config : IHolderConfig<T>> {

    companion object {
        fun <T> getEmptyViewHolder(context: Context) = object : CommonHolder<T>(View(context)) {
            override fun onBindData(data: T, position: Int) {}
            override fun upDataData(data: T, position: Int, payloads: MutableList<Any>) {}
        }
    }

    val viewType = mutableMapOf<String, Int>()

    abstract fun getItemViewType(position: Int, data: Any): Int

    fun <T> createViewHolder(parent: ViewGroup, viewType: Int): CommonHolder<T> {
        return null!!
    }
}