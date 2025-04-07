package com.ilyeong.movieverse.presentation.common.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilyeong.movieverse.databinding.ItemMovieGenreChipBinding
import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.presentation.util.ItemClickListener

class GenreViewHolder private constructor(
    private val binding: ItemMovieGenreChipBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: Genre, itemClickListener: ItemClickListener?) {
        binding.chipGenre.text = genre.name

        binding.root.setOnClickListener {
            itemClickListener?.onItemClick(genre.id)
        }
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