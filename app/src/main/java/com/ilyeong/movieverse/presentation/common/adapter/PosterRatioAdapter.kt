package com.ilyeong.movieverse.presentation.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.common.viewholder.PosterRatioViewHolder
import com.ilyeong.movieverse.presentation.util.ItemClickListener
import com.ilyeong.movieverse.presentation.util.MovieDiffUtil

class PosterRatioAdapter(
    private val itemClickListener: ItemClickListener
) : ListAdapter<Movie, PosterRatioViewHolder>(MovieDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PosterRatioViewHolder.create(parent)

    override fun onBindViewHolder(holder: PosterRatioViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }
}