package com.ilyeong.movieverse.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.ilyeong.movieverse.R

class MovieverseItemDecoration : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        val context = parent.context

        when (position) {
            0 -> {
                outRect.left =
                    context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
            }

            (itemCount - 1) -> {
                outRect.right =
                    context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
            }
        }
    }
}