package com.ilyeong.movieverse.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.home.viewholder.PosterDefaultViewHolder
import com.ilyeong.movieverse.presentation.util.MovieDiffUtil

class SectionAdapter : ListAdapter<Movie, PosterDefaultViewHolder>(MovieDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PosterDefaultViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: PosterDefaultViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}