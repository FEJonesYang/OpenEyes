package com.jonesyong.module_community.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jonesyong.library_common.content.CommonAdapter
import com.jonesyong.library_common.content.CommonHolder
import com.jonesyong.library_common.content.HolderFactory
import com.jonesyong.library_common.content.IHolderConfig
import com.jonesyong.module_community.api.model.CommunityData

class RecommendContentAdapter(override var holdFactory: HolderFactory<CommunityData, IHolderConfig<CommunityData>>?) : CommonAdapter<CommunityData>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonHolder<CommunityData> {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}