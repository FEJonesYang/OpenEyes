package com.jonesyong.library_common.content

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class CommonHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBindData(data: T, position: Int)
    abstract fun upDataData(data: T, position: Int, payloads: MutableList<Any>)
}