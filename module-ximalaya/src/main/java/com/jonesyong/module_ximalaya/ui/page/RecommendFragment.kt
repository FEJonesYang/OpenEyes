package com.jonesyong.module_ximalaya.ui.page

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_base.DataBindingConfig
import com.jonesyong.module_ximalaya.BR
import com.jonesyong.module_ximalaya.R
import com.jonesyong.module_ximalaya.databinding.FragmentXimalayaRecommendBinding
import com.jonesyong.module_ximalaya.ui.adapters.RecommendRecyclerAdapter
import com.jonesyong.module_ximalaya.ui.state.RecommendViewModel
import com.ximalaya.ting.android.opensdk.model.album.Album
import log.LogUtils

/**
 *  喜马拉雅模块推荐界面
 */
class RecommendFragment : BaseFragment() {

    private lateinit var mStateModel: RecommendViewModel
    private var mAdapter: RecommendRecyclerAdapter<Album>? = null
    private lateinit var mRecommendBinding: FragmentXimalayaRecommendBinding

    override fun initViewModel() {
        mStateModel = getFragmentScopeViewModel(RecommendViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_ximalaya_recommend, BR.vm, mStateModel)
            .addBindingParam(BR.adapter, RecommendRecyclerAdapter<Album>())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStateModel.recommendRequest.requestRecommendAlbumList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecommendBinding = (mBinding as FragmentXimalayaRecommendBinding)
        mAdapter = mBindingConfig.bindingParams[BR.adapter] as RecommendRecyclerAdapter<Album>
        mRecommendBinding.recyclerRecommend.layoutManager = LinearLayoutManager(context)
        mStateModel.recommendRequest.getRecommendLiveData().observe(viewLifecycleOwner) {
            mAdapter?.setDataList(it)
            if (it.size < 9) {
                mRecommendBinding.viewStubNoMoreDataView.viewStub?.inflate()
            }
        }
        // 处理界面的 Loading、Error 情况
        mStateModel.recommendRequest.isLoading().observe(viewLifecycleOwner) {
            if (it) {
                mRecommendBinding.includeNetworkLoading.containerNetworkLoading.visibility =
                    View.VISIBLE
            } else {
                mRecommendBinding.includeNetworkLoading.containerNetworkLoading.visibility =
                    View.GONE
            }
        }
        mStateModel.recommendRequest.isFailed().observe(viewLifecycleOwner) {
            if (it) {
                mRecommendBinding.includeNetworkError.containerNetworkError.visibility =
                    View.VISIBLE
                // 失败时，再次刷新的点击事件 TODO(是否可以对这个事件抽取出来？)
                mRecommendBinding.includeNetworkError.btnClickRefresh.setOnClickListener {
                    mStateModel.recommendRequest.isLoading().value = true
                    mStateModel.recommendRequest.requestRecommendAlbumList()
                    mStateModel.recommendRequest.isFailed().value = false
                }
            } else {
                mRecommendBinding.includeNetworkError.containerNetworkError.visibility =
                    View.GONE
            }
        }
    }
}