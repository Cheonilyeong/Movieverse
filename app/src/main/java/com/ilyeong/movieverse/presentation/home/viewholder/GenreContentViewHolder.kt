package com.ilyeong.movieverse.presentation.home.viewholder

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.ItemMovieGenreBinding
import com.ilyeong.movieverse.presentation.home.adapter.GenreContentAdapter
import com.ilyeong.movieverse.presentation.home.model.HomeContent.GenreContent

class GenreContentViewHolder private constructor(
    private val binding: ItemMovieGenreBinding
) : ViewHolder(binding.root) {

    fun bind(genreContent: GenreContent) {
        binding.rvMovieGenre.adapter = GenreContentAdapter(genreContent)
        binding.rvMovieGenre.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val itemCount = state.itemCount
                val context = parent.context

                if (position == 0) {
                    outRect.left =
                        context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                } else if (position == itemCount - 1) {
                    outRect.right =
                        context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                }
            }
        })
    }

    companion object {
        fun create(parent: ViewGroup): ViewHolder {
            val binding = ItemMovieGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return GenreContentViewHolder(binding)
        }
    }
}