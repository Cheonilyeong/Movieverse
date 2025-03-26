package com.ilyeong.movieverse.presentation.search.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import coil3.request.crossfade
import com.ilyeong.movieverse.databinding.ItemMoviePosterDescriptionBinding
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.util.ItemClickListener

class TrendViewHolder private constructor(
    private val binding: ItemMoviePosterDescriptionBinding
) : ViewHolder(binding.root) {

    fun bind(movie: Movie, itemClickListener: ItemClickListener) {
        binding.posterDefault.ivPoster.load(movie.posterPath) {
            crossfade(true)
            listener(
                onStart = { _ -> binding.posterDefault.tvPosterTitle.text = null },
                onError = { _, _ -> binding.posterDefault.tvPosterTitle.text = movie.title }
            )
        }
        binding.tvTitle.text = movie.title

        binding.rrv.rating = movie.voteAverage.toDouble()
        binding.rrv.ratingCount = movie.voteCount

        binding.tvDescription.text = movie.overview

        binding.root.setOnClickListener {
            itemClickListener.onItemClick(movie.id)
        }
    }

    companion object {
        fun create(parent: ViewGroup): TrendViewHolder {
            val binding = ItemMoviePosterDescriptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TrendViewHolder(binding)
        }
    }
}