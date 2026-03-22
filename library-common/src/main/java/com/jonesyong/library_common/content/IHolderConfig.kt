package com.jonesyong.library_common.content

import android.view.ViewGroup

interface IHolderConfig<T> {
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int)

    fun getItemViewType(position: Int)
}