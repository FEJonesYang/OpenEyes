package com.jonesyong.module_ximalaya.ui.page

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_base.DataBindingConfig
import com.jonesyong.library_foundation.util.view.AppCompatErrorView
import com.jonesyong.module_ximalaya.BR
import com.jonesyong.module_ximalaya.R
import com.jonesyong.module_ximalaya.api.data.Albums
import com.jonesyong.module_ximalaya.databinding.FragmentXimalayaRecommendBinding
import com.jonesyong.module_ximalaya.ui.adapters.RecommendRecyclerAdapter
import com.jonesyong.module_ximalaya.ui.vm.RecommendViewModel

/**
 *  喜马拉雅模块推荐界面
 */
class RecommendFragment : BaseFragment<RecommendViewModel>() {

    companion object {
        const val CATEGORY_ID = "category_id"

        fun newInstance(categoryId: Long): BaseFragment<RecommendViewModel> {
            val args = Bundle().apply {
                putLong(CATEGORY_ID, categoryId)
            }
            val fragment = RecommendFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mAdapter: RecommendRecyclerAdapter<Albums>? = null
    private lateinit var mRecommendBinding: FragmentXimalayaRecommendBinding

    private var categoryId: Int = 0

    override fun initViewModel() {
        vm = getFragmentScopeViewModel(RecommendViewModel::class.java)
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(
            R.layout.fragment_ximalaya_recommend,
            BR.vm,
            vm
        ).addBindingParam(BR.adapter, RecommendRecyclerAdapter<Albums>())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.requestRecommendAlbumList(categoryId)
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
        vm.recommendLiveData.observe(viewLifecycleOwner) {
            mAdapter?.setDataList(it)
        }
    }

    override fun inflateErrorView(view: View): AppCompatErrorView? =
        view.findViewById<AppCompatErrorView?>(R.id.network_error).apply {
            setTryAgainListener {
                vm.requestRecommendAlbumList(categoryId)
            }
        }
}