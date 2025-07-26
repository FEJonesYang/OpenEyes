package com.jonesyong.library_base

import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.size
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class DataBindingActivity : AppCompatActivity() {

    private var mBinding: ViewDataBinding? = null

    protected abstract fun initViewModel()

    protected abstract fun getDataBindingConfig(): DataBindingConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()

        val dataBindingConfig = getDataBindingConfig()
        val binding: ViewDataBinding =
            DataBindingUtil.setContentView(this, dataBindingConfig.layout)
        binding.lifecycleOwner = this
        binding.setVariable(
            dataBindingConfig.vmVariableId,
            dataBindingConfig.stateViewModel
        )
        val bindingParams: SparseArray<Any?> = dataBindingConfig.bindingParams

        for (index in 0..bindingParams.size) {
            binding.setVariable(bindingParams.keyAt(index), bindingParams.valueAt(index))
        }
        mBinding = binding
    }

    open fun isDebug(): Boolean {
        return this.applicationContext.applicationInfo != null && this.applicationContext.applicationInfo.flags and 2 != 0
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
        mBinding = null
    }

}