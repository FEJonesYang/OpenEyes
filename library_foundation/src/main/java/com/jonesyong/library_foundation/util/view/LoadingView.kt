package com.jonesyong.library_foundation.util.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.widget.AppCompatImageView
import com.jonesyong.library_foundation.R
import com.jonesyong.library_foundation.util.ktx.setRoundCorner

class LoadingView(
    context: Context, @StyleRes style: Int = R.style.FullScreenTransparentDialog
) : AppCompatDialog(context, style) {

    private var rotateAnim: ObjectAnimator? = null

    init {
        setContentView(R.layout.network_loading_layout)
        findViewById<LinearLayout>(R.id.ll_loading_root)?.apply {
            setRoundCorner(24f)
        }
        rotateAnim = ObjectAnimator.ofFloat(
            findViewById<AppCompatImageView>(R.id.iv_network_loading),
            "rotation",
            0f,
            360f
        ).apply {
            duration = 1500L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
        }
    }

    override fun show() {
        super.show()
        rotateAnim?.start()
    }

    override fun dismiss() {
        super.dismiss()
        rotateAnim?.cancel()
    }

    companion object {
        private var instance: LoadingView? = null

        fun show(context: Context?) {
            if (instance != null && instance?.isShowing == true) {
                return
            }
            if (context == null) {
                return
            }
            instance = LoadingView(context)
            instance?.show()
        }

        fun hide() {
            if (instance == null) {
                return
            }
            instance?.dismiss()
            instance = null
        }
    }
}