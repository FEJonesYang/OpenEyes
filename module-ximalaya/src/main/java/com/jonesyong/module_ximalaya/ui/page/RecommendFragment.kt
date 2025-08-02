package com.jonesyong.module_ximalaya.ui.page

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_base.DataBindingConfig
import com.jonesyong.module_ximalaya.BR
import com.jonesyong.module_ximalaya.R
import com.jonesyong.module_ximalaya.api.data.Albums
import com.jonesyong.module_ximalaya.databinding.FragmentXimalayaRecommendBinding
import com.jonesyong.module_ximalaya.ui.adapters.RecommendRecyclerAdapter
import com.jonesyong.module_ximalaya.ui.vm.RecommendViewModel

/**
 *  喜马拉雅模块推荐界面
 */
class RecommendFragment : BaseFragment() {

    companion object {
        const val CATEGORY_ID = "category_id"

        fun newInstance(categoryId: Long): BaseFragment {
            val args = Bundle().apply {
                putLong(CATEGORY_ID, categoryId)
            }
            val fragment = RecommendFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mStateModel: RecommendViewModel
    private var mAdapter: RecommendRecyclerAdapter<Albums>? = null
    private lateinit var mRecommendBinding: FragmentXimalayaRecommendBinding

    private var categoryId: Int = 0

    override fun initViewModel() {
        mStateModel = getFragmentScopeViewModel(RecommendViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(
            R.layout.fragment_ximalaya_recommend,
            BR.vm,
            mStateModel
        ).addBindingParam(BR.adapter, RecommendRecyclerAdapter<Albums>())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStateModel.requestRecommendAlbumList(categoryId)
    }

    override fun parseParams() {
        super.parseParams()
        categoryId = arguments?.getInt(CATEGORY_ID, 0) ?: 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecommendBinding = (mBinding as FragmentXimalayaRecommendBinding)
        mAdapter = mBindingConfig.bindingParams[BR.adapter] as RecommendRecyclerAdapter<Albums>
        mRecommendBinding.recyclerRecommend.layoutManager = LinearLayoutManager(context)
    }

    override fun onSubscribeUi(view: View) {
        super.onSubscribeUi(view)
        mStateModel.recommendLiveData.observe(viewLifecycleOwner) {
            mAdapter?.setDataList(it)
        }
    }
}