package com.ilyeong.movieverse.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible

class CustomProgressBar(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val progressBar: ProgressBar = ProgressBar(context).apply {
        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    var isLoading: Boolean = false
        set(value) {
            field = value
            this.isVisible = value
            this.isClickable = value
        }

    init {
        gravity = Gravity.CENTER
        addView(progressBar)
    }
}