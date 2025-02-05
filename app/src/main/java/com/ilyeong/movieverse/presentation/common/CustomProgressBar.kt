package com.ilyeong.movieverse.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.ilyeong.movieverse.R

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
        addView(progressBar)

        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, 0, 0)
            .apply {
                try {
                    isLoading = getBoolean(R.styleable.CustomProgressBar_isLoading, false)
                } finally {
                    recycle()
                }
            }
    }
}