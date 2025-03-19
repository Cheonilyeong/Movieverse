package com.ilyeong.movieverse.presentation.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Review
import com.ilyeong.movieverse.presentation.detail.viewholder.ReviewViewHolder

class ReviewAdapter : ListAdapter<Review, ReviewViewHolder>(reviewDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ReviewViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: ReviewViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}

private val reviewDiffUtil = object : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(
        oldItem: Review,
        newItem: Review
    ) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(
        oldItem: Review,
        newItem: Review
    ) = (oldItem == newItem)
}