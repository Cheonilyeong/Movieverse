package com.ilyeong.movieverse.presentation.util

import androidx.recyclerview.widget.RecyclerView
import com.ilyeong.movieverse.R

fun RecyclerView.calculateSpanCount(
    margin: Int = resources.getDimensionPixelSize(R.dimen.movieverse_padding_large) * 2
): Int {
    val screenWidth = resources.displayMetrics.widthPixels
    val viewWidth = screenWidth - margin
    val itemWidth = resources.getDimensionPixelSize(R.dimen.movieverse_poster_default_width)

    return (viewWidth / itemWidth)
}