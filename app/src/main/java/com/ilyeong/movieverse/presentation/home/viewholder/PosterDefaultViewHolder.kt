package com.ilyeong.movieverse.presentation.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import coil3.request.crossfade
import com.ilyeong.movieverse.databinding.ItemMoviePosterDefaultBinding
import com.ilyeong.movieverse.domain.model.Movie

class PosterDefaultViewHolder private constructor(
    private val binding: ItemMoviePosterDefaultBinding
) : ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.ivPoster.load(movie.posterPath) {
            crossfade(true)
            listener(
                onStart = { _ -> binding.tvTitle.text = null },
                onError = { _, _ -> binding.tvTitle.text = movie.title }
            )
        }
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