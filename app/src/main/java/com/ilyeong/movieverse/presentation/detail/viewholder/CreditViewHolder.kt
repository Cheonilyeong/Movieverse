package com.ilyeong.movieverse.presentation.detail.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil3.load
import coil3.request.crossfade
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.ItemMovieCastBinding
import com.ilyeong.movieverse.domain.model.Cast

class CastViewHolder private constructor(
    private val binding: ItemMovieCastBinding
) : ViewHolder(binding.root) {

    fun bind(cast: Cast) {
        binding.ivCast.load(cast.profilePath) {
            crossfade(true)
        }
        binding.tvName.text = cast.name
        binding.tvCharacter.text =
            binding.root.context.getString(R.string.character, cast.character)
    }

    companion object {
        fun from(parent: ViewGroup): CastViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMovieCastBinding.inflate(layoutInflater, parent, false)
            return CastViewHolder(binding)
        }
    }
}