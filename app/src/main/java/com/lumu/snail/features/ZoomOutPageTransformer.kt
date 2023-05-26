package com.lumu.snail.features

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {

        val minScale = 0.90f
        val minAlpha = 0.85f
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the top.
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    // Modify the default slide transition to shrink the page as well.
                    val scaleFactor = minScale.coerceAtLeast(1 - abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    translationY = if (position < 0) {
                        vertMargin - horzMargin / 2
                    } else {
                        vertMargin + horzMargin / 2
                    }

                    // Scale the page down (between MIN_SCALE and 1).
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // Fade the page relative to its size.
                    alpha = (minAlpha +
                            (((scaleFactor - minScale) / (1 - minScale)) * (1 - minAlpha)))
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the bottom.
                    alpha = 0f
                }
            }
        }
    }
}