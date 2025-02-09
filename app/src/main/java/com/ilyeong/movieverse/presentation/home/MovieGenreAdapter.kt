package com.ilyeong.movieverse.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.databinding.ItemMovieGenreBinding

class MovieGenreAdapter(private val movieGenreList: List<String>) :
    Adapter<MovieGenreAdapter.ViewHolder>() {

    class ViewHolder private constructor(private val binding: ItemMovieGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieGenre: String) {
            binding.chipGenre.text = movieGenre
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val binding = ItemMovieGenreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(movieGenreList[position])
    }

    override fun getItemCount(): Int {
        return movieGenreList.size
    }
}