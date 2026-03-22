package com.jonesyong.library_common.content

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class CommonAdapter<T : Any> : RecyclerView.Adapter<CommonHolder<T>>() {

    private val data = mutableListOf<T>()

    abstract var holdFactory: HolderFactory<T, IHolderConfig<T>>?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonHolder<T> {
        return holdFactory?.createViewHolder(parent, viewType) ?: HolderFactory.getEmptyViewHolder(
            parent.context
        )
    }

    override fun getItemViewType(position: Int): Int {
        return holdFactory?.getItemViewType(position, data[position]) ?: -1
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        holder: CommonHolder<T>, position: Int, payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()) {
            holder.onBindData(data[position], position)
        } else {
            holder.upDataData(data[position], position, payloads)
        }
    }

    override fun onBindViewHolder(holder: CommonHolder<T>, position: Int) {}
}






