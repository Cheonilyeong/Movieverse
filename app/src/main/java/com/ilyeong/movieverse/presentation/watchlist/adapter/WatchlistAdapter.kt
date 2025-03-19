package com.ilyeong.movieverse.presentation.watchlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.util.MovieClickListener
import com.ilyeong.movieverse.presentation.util.MovieDiffUtil
import com.ilyeong.movieverse.presentation.watchlist.viewholder.WatchlistViewHolder

class WatchlistAdapter(
    private val movieClickListener: MovieClickListener
) : ListAdapter<Movie, WatchlistViewHolder>(MovieDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WatchlistViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: WatchlistViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), movieClickListener)
    }
}