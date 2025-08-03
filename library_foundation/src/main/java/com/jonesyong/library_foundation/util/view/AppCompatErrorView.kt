package com.jonesyong.library_foundation.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.jonesyong.library_foundation.R

class AppCompatErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mTryAgain: View? = null
    private var mTryAgainListener: (() -> Unit)? = null

    init {
        inflate(context, R.layout.network_error_layout, this)
        mTryAgain = findViewById(R.id.btn_click_refresh)
        mTryAgain?.setOnClickListener {
            mTryAgainListener?.invoke()
        }
    }

    fun setTryAgainListener(l: () -> Unit) {
        mTryAgainListener = l
    }

    fun show() {
        visibility = View.VISIBLE
    }

    fun hide() {
        visibility = View.GONE
    }
}