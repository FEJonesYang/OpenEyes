package com.jonesyong.module_community.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.module_community.R
import com.jonesyong.module_community.vm.RecommendVm


/**
 * A simple [Fragment] subclass.
 * Use the [RecommendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecommendFragment : BaseFragment<RecommendVm>() {

    companion object {
        fun newInstance(): RecommendFragment {
            val args = Bundle()
            val fragment = RecommendFragment().apply {
                arguments = args
            }
            return fragment
        }
    }

    override fun getInflateId(): Int = R.layout.fragment_recommend

    override fun initViewModel() {
        super.initViewModel()
        vm = getFragmentScopeViewModel(RecommendVm::class.java)
    }

}