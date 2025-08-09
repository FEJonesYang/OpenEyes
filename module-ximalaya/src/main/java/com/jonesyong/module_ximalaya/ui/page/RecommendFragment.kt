package com.jonesyong.module_ximalaya.ui.page

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_foundation.util.view.AppCompatErrorView
import com.jonesyong.module_ximalaya.R
import com.jonesyong.module_ximalaya.api.data.Albums
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

    private var mRecyclerView:RecyclerView?= null
    private var mAdapter: RecommendRecyclerAdapter<Albums>? = null

    private var categoryId: Int = 0

    override fun initViewModel() {
        super.initViewModel()
        vm = getFragmentScopeViewModel(RecommendViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.requestRecommendAlbumList(categoryId)
    }

    override fun parseParams() {
        super.parseParams()
        categoryId = arguments?.getInt(CATEGORY_ID, 0) ?: 0
    }

    override fun getInflateId(): Int = R.layout.fragment_ximalaya_recommend

    override fun initView(root: View) {
        super.initView(root)
        mAdapter = RecommendRecyclerAdapter()
        mRecyclerView = root.findViewById(R.id.recycler_recommend)
        mRecyclerView?.adapter = mAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
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