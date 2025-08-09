package com.jonesyong.library_foundation.util.ktx

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

fun View.setRoundCorner(radiusDp: Float = 24f) {
    val radius = radiusDp * resources.displayMetrics.density
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(view.left, view.top, view.width, view.height, radius)
        }
    }
    clipToOutline = true // 开启裁剪
}