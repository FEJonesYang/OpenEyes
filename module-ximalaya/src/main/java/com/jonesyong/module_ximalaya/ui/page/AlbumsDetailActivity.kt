package com.jonesyong.module_ximalaya.ui.page

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonesyong.library_base.BaseActivity
import com.jonesyong.module_ximalaya.R
import com.jonesyong.module_ximalaya.api.data.Tracks
import com.jonesyong.module_ximalaya.ui.adapters.AlbumsDetailAdapter
import com.jonesyong.module_ximalaya.ui.vm.AlbumsDetailViewModel

class AlbumsDetailActivity : BaseActivity<AlbumsDetailViewModel>() {

    private var albumId: Int? = null

    companion object {
        private const val ALBUM_ID = "album_id"

        @JvmStatic
        fun start(context: Context, albumId: Int) {
            val intent = Intent(context, AlbumsDetailActivity::class.java)
                .putExtra(ALBUM_ID, albumId)
            context.startActivity(intent)
        }
    }

    private var mAlbumsDetailAdapter: AlbumsDetailAdapter<Tracks>? = null
    private var mRecyclerView:RecyclerView?= null

    override fun parseParams() {
        super.parseParams()
        albumId = intent?.getIntExtra(ALBUM_ID, 0)
    }

    override fun initViewModel() {
        super.initViewModel()
        vm = getActivityScopeViewModel(AlbumsDetailViewModel::class.java)
        vm.fetchAlbumsBrowse(albumId)
    }

    override fun getInflateId(): Int = R.layout.activity_albums_detail

    override fun initView() {
        super.initView()
        mAlbumsDetailAdapter = AlbumsDetailAdapter()
        mRecyclerView = findViewById(R.id.rv_albums_detail)
        mRecyclerView?.adapter = mAlbumsDetailAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
    }

    override fun onSubscribeUi() {
        super.onSubscribeUi()
        vm.albumsDetail.observe(this) {
            mAlbumsDetailAdapter?.setDataList(it?.tracks)
        }
    }

}