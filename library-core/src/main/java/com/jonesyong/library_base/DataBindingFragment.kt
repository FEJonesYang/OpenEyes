package com.jonesyong.library_base

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.size
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author: Jony
 * @date: 2022/5/9
 * @description:
 */
abstract class DataBindingFragment : Fragment() {

    private lateinit var mActivity: AppCompatActivity
    protected var mBinding: ViewDataBinding? = null
    private var mTvStrictModeTip: TextView? = null


    protected val mBindingConfig: DataBindingConfig by lazy {
        getDataBindingConfig()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    protected abstract fun initViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    protected abstract fun getDataBindingConfig(): DataBindingConfig

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, mBindingConfig.layout, null, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.setVariable(
            mBindingConfig.vmVariableId,
            mBindingConfig.stateViewModel
        )
        val bindingParams: SparseArray<Any?> = mBindingConfig.bindingParams
        for (index in 0 until bindingParams.size) {
            binding.setVariable(bindingParams.keyAt(index), bindingParams.valueAt(index))
        }
        mBinding = binding
        return binding.root
    }

    open fun isDebug(): Boolean {
        return mActivity.applicationContext.applicationInfo != null && (mActivity.applicationContext.applicationInfo.flags and 2) != 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding!!.unbind()
        mBinding = null
    }
}