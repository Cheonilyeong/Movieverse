package com.ilyeong.movieverse.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.presentation.home.viewholder.GenreViewHolder

class GenreAdapter : ListAdapter<Genre, GenreViewHolder>(genreDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = GenreViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: GenreViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}

private val genreDiffUtil = object : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(
        oldItem: Genre,
        newItem: Genre
    ) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(
        oldItem: Genre,
        newItem: Genre
    ) = (oldItem == newItem)
}