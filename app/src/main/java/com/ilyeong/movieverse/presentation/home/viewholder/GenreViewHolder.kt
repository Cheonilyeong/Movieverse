package com.ilyeong.movieverse.presentation.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyeong.movieverse.databinding.ItemMovieGenreChipBinding
import com.ilyeong.movieverse.domain.model.Genre

class GenreViewHolder private constructor(private val binding: ItemMovieGenreChipBinding) :
    ViewHolder(binding.root) {

    fun bind(genre: Genre) {
        binding.chipGenre.text = genre.name
    }

    companion object {
        fun create(parent: ViewGroup): GenreViewHolder {
            val binding = ItemMovieGenreChipBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return GenreViewHolder(binding)
        }
    }
}