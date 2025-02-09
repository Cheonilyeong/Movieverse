package com.ilyeong.movieverse.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.databinding.ItemMoviePosterDefaultBinding
import com.ilyeong.movieverse.domain.model.Movie

class MovieListAdapter(private val movieList: List<Movie>) :
    Adapter<MovieListAdapter.ViewHolder>() {

    class ViewHolder private constructor(private val binding: ItemMoviePosterDefaultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            // todo
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemMoviePosterDefaultBinding.inflate(
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
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}