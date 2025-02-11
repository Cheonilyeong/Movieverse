package com.ilyeong.movieverse.presentation.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilyeong.movieverse.databinding.ItemMoviePosterFullBinding
import com.ilyeong.movieverse.domain.model.Movie

class PosterFullViewHolder private constructor(binding: ItemMoviePosterFullBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) = Unit

    companion object {
        fun create(parent: ViewGroup): PosterFullViewHolder {
            val binding =
                ItemMoviePosterFullBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return PosterFullViewHolder(binding)
        }
    }
}