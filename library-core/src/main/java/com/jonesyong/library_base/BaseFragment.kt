package com.jonesyong.library_base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.library_foundation.util.view.AppCompatErrorView
import com.jonesyong.library_foundation.util.view.LoadingView


abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    open lateinit var vm: VM

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
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getInflateId(),null,false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)
        initView(root)
        onSubscribeUi(root)
        errorView = inflateErrorView(root)
    }

    open fun onSubscribeUi(view: View) {
        vm.error.observe(viewLifecycleOwner) {
            if (it) {
                errorView?.show()
            } else {
                errorView?.hide()
            }
        }
        vm.loading.observe(viewLifecycleOwner) {
            if (it) {
                LoadingView.show(context)
            } else {
                LoadingView.hide()
            }
        }
    }

    private var errorView: AppCompatErrorView? = null
    open fun inflateErrorView(view: View): AppCompatErrorView? = null

    open fun parseParams() {}

    abstract fun getInflateId() : Int

    open fun initViewModel() {}

    open fun initView(root: View) {}
}