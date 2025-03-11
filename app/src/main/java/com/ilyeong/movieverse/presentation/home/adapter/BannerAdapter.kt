package com.ilyeong.movieverse.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.home.viewholder.PosterFullViewHolder
import com.ilyeong.movieverse.presentation.util.MovieDiffUtil

class BannerAdapter : ListAdapter<Movie, PosterFullViewHolder>(MovieDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PosterFullViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: PosterFullViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}