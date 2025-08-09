package com.jonesyong.module_ximalaya.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.jonesyong.library_foundation.util.ImageUtil
import com.jonesyong.module_ximalaya.R
import com.jonesyong.module_ximalaya.api.data.Tracks

class AlbumsDetailAdapter<T> : RecyclerViewAdapter<T, AlbumsDetailAdapter.ViewHolder>() {

    private val TAG = "AlbumsDetailAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_albums_detail_layout, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mDataList[position] as? Tracks ?: return
        holder.tvTitleView.text = data.track_title
        ImageUtil.loadRoundedImage(holder.itemView.context, data.cover_url_small, 20)
            .into(holder.ivHeaderImage)
        holder.tvContentView.text = data.track_title
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivHeaderImage: AppCompatImageView
        var tvTitleView: TextView
        var tvContentView: TextView

        init {
            ivHeaderImage = itemView.findViewById(R.id.iv_album_order)
            tvTitleView = itemView.findViewById(R.id.tv_album_title)
            tvContentView = itemView.findViewById(R.id.tv_albums_detail_content)
        }
    }
}