package com.jonesyong.module_ximalaya.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jonesyong.library_foundation.util.ImageUtil
import com.jonesyong.module_ximalaya.R
import com.jonesyong.module_ximalaya.api.data.Albums
import com.jonesyong.module_ximalaya.ui.page.AlbumsDetailActivity

/**
 * @author: Jony
 * @date: 2022/5/13
 * @description: 喜马拉雅推荐数据的适配器
 */
class RecommendRecyclerAdapter<T> : RecyclerViewAdapter<T, RecommendRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommend_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album: Albums = mDataList[position] as Albums
        holder.mPlayCount?.text = album.play_count.toString()
        holder.mIntroduce?.text = album.album_intro
        holder.mTitle?.text = album.album_title
        // 圆角封面
        ImageUtil.loadRoundedImage(holder.itemView.context, album.cover_url_small, 20)
            .into(holder.mImageCover!!)
        holder.itemView.setOnClickListener {
            AlbumsDetailActivity.start(holder.itemView.context, album.id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mImageCover: ImageView? = null
        var mTitle: TextView? = null
        var mIntroduce: TextView? = null
        var mPlayCount: TextView? = null

        init {
            mImageCover = itemView.findViewById(R.id.iv_recommend_item)
            mTitle = itemView.findViewById(R.id.tv_recommend_item_title)
            mIntroduce = itemView.findViewById(R.id.tv_recommend_item_introduce)
            mPlayCount = itemView.findViewById(R.id.tv_recommend_item_play_count)
        }
    }

}