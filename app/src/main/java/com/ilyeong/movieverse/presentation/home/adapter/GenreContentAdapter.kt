package com.ilyeong.movieverse.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.presentation.home.model.HomeContent.GenreContent
import com.ilyeong.movieverse.presentation.home.viewholder.GenreViewHolder

class GenreContentAdapter(
    private val genreContent: GenreContent
) : Adapter<GenreViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreViewHolder {
        return GenreViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: GenreViewHolder,
        position: Int
    ) {
        holder.bind(genreContent.genreList[position])
    }

    override fun getItemCount() = genreContent.genreList.size
}