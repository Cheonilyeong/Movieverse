package com.ilyeong.movieverse.presentation.common.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.common.viewholder.PosterRatioViewHolder
import com.ilyeong.movieverse.presentation.util.ItemClickListener
import com.ilyeong.movieverse.presentation.util.MovieDiffUtil

class PosterRatioPagingAdapter(
    private val itemClickListener: ItemClickListener
) : PagingDataAdapter<Movie, PosterRatioViewHolder>(MovieDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PosterRatioViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: PosterRatioViewHolder,
        position: Int
    ) {
        val item = getItem(position) ?: return
        holder.bind(item, itemClickListener)
    }
}