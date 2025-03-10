package com.ilyeong.movieverse.presentation.watchlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.watchlist.viewholder.WatchlistViewHolder

class WatchlistAdapter(private val watchlist: List<Movie>) : Adapter<WatchlistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        return WatchlistViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: WatchlistViewHolder,
        position: Int
    ) {
        holder.bind(watchlist[position])
    }

    override fun getItemCount() = watchlist.size
}