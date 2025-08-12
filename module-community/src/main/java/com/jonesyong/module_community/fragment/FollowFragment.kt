package com.jonesyong.module_community.fragment

import android.os.Bundle
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.module_community.R
import com.jonesyong.module_community.vm.FollowVm

class FollowFragment : BaseFragment<FollowVm>() {

    companion object {
        fun newInstance(): FollowFragment {
            val args = Bundle()
            val fragment = FollowFragment().apply {
                arguments = args
            }
            return fragment
        }
    }

    override fun getInflateId(): Int = R.layout.fragment_follow

    override fun initViewModel() {
        super.initViewModel()
        vm = getFragmentScopeViewModel(FollowVm::class.java)
    }
}