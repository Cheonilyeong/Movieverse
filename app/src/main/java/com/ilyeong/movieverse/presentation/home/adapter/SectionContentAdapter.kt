package com.ilyeong.movieverse.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.home.viewholder.PosterDefaultViewHolder

class SectionContentAdapter(
    private val movieList: List<Movie>
) : Adapter<PosterDefaultViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PosterDefaultViewHolder {
        return PosterDefaultViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: PosterDefaultViewHolder,
        position: Int
    ) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size
}