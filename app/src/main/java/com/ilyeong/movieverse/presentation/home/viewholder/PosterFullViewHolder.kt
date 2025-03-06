package com.ilyeong.movieverse.presentation.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.ilyeong.movieverse.databinding.ItemMoviePosterFullBinding
import com.ilyeong.movieverse.domain.model.Movie

class PosterFullViewHolder private constructor(private val binding: ItemMoviePosterFullBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.ivPoster.load(movie.posterPath) {
            crossfade(true)
            listener(
                onSuccess = { _, _ -> binding.tvTitle.text = null },
                onError = { _, _ -> binding.tvTitle.text = movie.title }
            )
        }
    }

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