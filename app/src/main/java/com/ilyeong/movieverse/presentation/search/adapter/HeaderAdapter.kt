package com.ilyeong.movieverse.presentation.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.presentation.search.viewholder.HeaderViewHolder

class HeaderAdapter(
    private val title: String
) : Adapter<HeaderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = HeaderViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: HeaderViewHolder,
        position: Int
    ) {
        holder.bind(title)
    }

    override fun getItemCount() = 1
}