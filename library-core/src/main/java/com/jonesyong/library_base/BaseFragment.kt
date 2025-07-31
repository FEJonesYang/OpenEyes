package com.jonesyong.library_base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment : DataBindingFragment() {

    /**
     * 获取 Fragment 范围内的 ViewModel
     */
    protected open fun <T : ViewModel?> getFragmentScopeViewModel(modelClass: Class<T>): T =
        ViewModelProvider(this).get(modelClass)

    /**
     * 获取 Activity 范围内的 ViewModel
     */
    protected open fun <T : ViewModel?> getActivityScopeViewModel(modelClass: Class<T>): T =
        ViewModelProvider(requireActivity()).get(modelClass)
}