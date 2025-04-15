package com.ilyeong.movieverse.presentation.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.common.viewholder.PosterDescriptionViewHolder
import com.ilyeong.movieverse.presentation.util.ItemClickListener
import com.ilyeong.movieverse.presentation.util.MovieDiffUtil

class PosterDescriptionAdapter(
    private val itemClickListener: ItemClickListener
) : ListAdapter<Movie, PosterDescriptionViewHolder>(MovieDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PosterDescriptionViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: PosterDescriptionViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), itemClickListener)
    }
}