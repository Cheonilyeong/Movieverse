package com.ilyeong.movieverse.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ilyeong.movieverse.R

class ReviewRatingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val starImageViews = mutableListOf<ImageView>()
    private val ratingTextView: TextView

    var rating: Double = 0.0
        set(value) {
            field = value
            val fullStars = (value / 2).toInt()
            val halfStar = if (value % 2 >= 1) 1 else 0
            ratingTextView.text = "$value"
            updateStars(fullStars, halfStar)
        }

    var ratingCount: Int = 0
        set(value) {
            field = value
            ratingTextView.text = "($ratingCount)"
        }

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        repeat(5) {
            val starImageView = ImageView(context).apply {
                layoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                )
            }
            starImageViews.add(starImageView)
            addView(starImageView)
        }

        ratingTextView = TextView(context).apply {
            setTextColor(ContextCompat.getColor(context, R.color.gray))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = dpToPx(4)
            }
        }
        addView(ratingTextView)
    }

    private fun updateStars(fullStars: Int, halfStar: Int) {
        for (i in 0 until 5) {
            val starImageView = starImageViews[i]
            when {
                i < fullStars -> {
                    starImageView.setImageResource(R.drawable.ic_star_filled_12)
                }

                i == fullStars && halfStar == 1 -> {
                    starImageView.setImageResource(R.drawable.ic_star_half_12)
                }

                else -> {
                    starImageView.setImageResource(R.drawable.ic_star_outlined_12)
                }
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}