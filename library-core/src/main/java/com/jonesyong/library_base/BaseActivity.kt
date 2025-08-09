package com.jonesyong.library_base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jonesyong.library_base.model.BaseViewModel
import com.jonesyong.library_foundation.util.view.AppCompatErrorView
import com.jonesyong.library_foundation.util.view.LoadingView

abstract class BaseActivity<VM : BaseViewModel> : FragmentActivity() {

    open lateinit var vm: VM

    private val mActivityProvider: ViewModelProvider by lazy {
        ViewModelProvider(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getInflateId())
        parseParams()
        initViewModel()
        initView()
        onSubscribeUi()
    }

    protected open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        return mActivityProvider.get(modelClass)
    }

    open fun onSubscribeUi() {
        vm.error.observe(this) {
            if (it) {
                errorView?.show()
            } else {
                errorView?.hide()
            }
        }
        vm.loading.observe(this) {
            if (it) {
                LoadingView.show(this)
            } else {
                LoadingView.hide()
            }
        }
    }

    private var errorView: AppCompatErrorView? = null
    open fun inflateErrorView(view: View): AppCompatErrorView? = null

    open fun parseParams() {}

    open  fun initViewModel() {}

    open fun initView() {}

    abstract fun getInflateId() : Int
}



