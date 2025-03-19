package com.ilyeong.movieverse.presentation.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ilyeong.movieverse.domain.model.Cast
import com.ilyeong.movieverse.presentation.detail.viewholder.CastViewHolder

class CastAdapter : ListAdapter<Cast, CastViewHolder>(castDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CastViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: CastViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

}

private val castDiffUtil = object : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(
        oldItem: Cast,
        newItem: Cast
    ) = (oldItem.id == newItem.id)

    override fun areContentsTheSame(
        oldItem: Cast,
        newItem: Cast
    ) = (oldItem == newItem)
}