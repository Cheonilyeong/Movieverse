package com.ilyeong.movieverse.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyeong.movieverse.databinding.ItemMoviePosterFullBinding
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.home.MovieBannerAdapter.MovieBannerViewHolder

class MovieBannerAdapter(private val posterList: List<Movie>) : Adapter<MovieBannerViewHolder>() {

    class MovieBannerViewHolder private constructor(binding: ItemMoviePosterFullBinding) :
        ViewHolder(binding.root) {

        companion object {
            fun create(parent: ViewGroup): MovieBannerViewHolder {
                val binding =
                    ItemMoviePosterFullBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return MovieBannerViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieBannerViewHolder {
        return MovieBannerViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: MovieBannerViewHolder,
        position: Int
    ) {
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return posterList.size
    }
}