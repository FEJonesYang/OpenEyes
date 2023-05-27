package com.jonesyong.library_base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment : DataBindingFragment() {

    /**
     * ViewModelProvider 延迟初始化
     */
    private val mFragmentProvider: ViewModelProvider by lazy {
        ViewModelProvider(this)
    }

    /**
     * 获取 Fragment 范围内的 ViewModel
     */
    protected open fun <T : ViewModel?> getFragmentScopeViewModel(modelClass: Class<T>): T {
        return mFragmentProvider.get(modelClass)
    }

}