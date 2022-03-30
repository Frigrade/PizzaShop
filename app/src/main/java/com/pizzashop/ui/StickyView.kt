package com.pizzashop.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import com.pizzashop.R

class StickyView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
) : LinearLayout(context, attributeSet) {
    private var yScroll = 0
    private var initialY: Float = Float.MIN_VALUE

    val scrollListener = NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
        yScroll = scrollY
        invalidate()
    }

    init {
        setWillNotDraw(false)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (initialY == Float.MIN_VALUE) {
            initialY = y
        }
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {

        if (y >= initialY) {
            y = yScroll.toFloat()
        }
        if (y <= initialY) {
            y = initialY
        }
        super.onDraw(canvas)
    }
}