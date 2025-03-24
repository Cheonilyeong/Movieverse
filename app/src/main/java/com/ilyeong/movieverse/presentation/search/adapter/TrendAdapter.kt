package com.ilyeong.movieverse.presentation.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.search.viewholder.TrendViewHolder
import com.ilyeong.movieverse.presentation.util.MovieDiffUtil

class TrendAdapter : ListAdapter<Movie, TrendViewHolder>(MovieDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TrendViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: TrendViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

}