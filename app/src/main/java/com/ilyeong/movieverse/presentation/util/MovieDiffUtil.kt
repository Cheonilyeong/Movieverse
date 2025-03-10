package com.ilyeong.movieverse.presentation.util

import androidx.recyclerview.widget.DiffUtil
import com.ilyeong.movieverse.domain.model.Movie

object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ) = (oldItem == newItem)
}