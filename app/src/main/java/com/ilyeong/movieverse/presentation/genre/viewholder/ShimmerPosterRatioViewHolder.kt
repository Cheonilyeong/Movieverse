package com.ilyeong.movieverse.presentation.genre.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilyeong.movieverse.databinding.ShimmerItemMoviePosterRatioSizeBinding

class ShimmerPosterRatioViewHolder private constructor(
    binding: ShimmerItemMoviePosterRatioSizeBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): ShimmerPosterRatioViewHolder {
            return ShimmerPosterRatioViewHolder(
                ShimmerItemMoviePosterRatioSizeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}