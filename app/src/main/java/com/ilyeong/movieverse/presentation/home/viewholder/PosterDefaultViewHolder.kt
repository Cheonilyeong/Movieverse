package com.ilyeong.movieverse.presentation.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyeong.movieverse.databinding.ItemMoviePosterDefaultBinding
import com.ilyeong.movieverse.domain.model.Movie

class PosterDefaultViewHolder private constructor(
    private val binding: ItemMoviePosterDefaultBinding
) : ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        // todo
    }

    companion object {
        fun create(parent: ViewGroup): PosterDefaultViewHolder {
            val binding =
                ItemMoviePosterDefaultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return PosterDefaultViewHolder(binding)
        }
    }
}