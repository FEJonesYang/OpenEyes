package com.jonesyong.library_base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment : DataBindingFragment() {

    /**
     * 获取 Fragment 范围内的 ViewModel
     */
    protected open fun <T : ViewModel?> getFragmentScopeViewModel(modelClass: Class<T>): T =
        ViewModelProvider(this).get(modelClass)

    /**
     * 获取父 Fragment 范围内的 ViewModel
     */
    protected open fun <T : ViewModel?> getParentFragmentScopeViewModel(modelClass: Class<T>): T? =
        parentFragment?.let { ViewModelProvider(it).get(modelClass) }

    /**
     * 获取 Activity 范围内的 ViewModel
     */
    protected open fun <T : ViewModel?> getActivityScopeViewModel(modelClass: Class<T>): T =
        ViewModelProvider(requireActivity()).get(modelClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSubscribeUi(view)
    }

    open fun onSubscribeUi(view: View) {}

    open fun parseParams() {}
}