package com.ilyeong.movieverse.presentation.home

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.ItemMovieSectionBinding
import com.ilyeong.movieverse.presentation.home.model.MovieSection

class MovieSectionListAdapter(private val movieSectionList: List<MovieSection>) :
    Adapter<MovieSectionListAdapter.ViewHolder>() {

    class ViewHolder private constructor(private val binding: ItemMovieSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieSection: MovieSection) {
            binding.tvTitle.text = movieSection.title
            binding.rvMovieList.adapter = MovieListAdapter(movieSection.movieList)

            binding.rvMovieList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    val itemCount = state.itemCount
                    val context = parent.context

                    if (position == 0) {
                        // 첫 번째 아이템
                        outRect.left =
                            context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                    } else if (position == itemCount - 1) {
                        // 마지막 아이템
                        outRect.right =
                            context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                    }
                }
            })
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemMovieSectionBinding.inflate(
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
        holder.bind(movieSectionList[position])
    }

    override fun getItemCount(): Int {
        return movieSectionList.size
    }
}