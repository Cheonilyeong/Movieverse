package com.ilyeong.movieverse.presentation.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.common.viewholder.PosterFixedViewHolder
import com.ilyeong.movieverse.presentation.util.ItemClickListener
import com.ilyeong.movieverse.presentation.util.MovieDiffUtil

class PosterFixedAdapter(private val itemClickListener: ItemClickListener) :
    ListAdapter<Movie, PosterFixedViewHolder>(MovieDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PosterFixedViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: PosterFixedViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), itemClickListener)
    }
}