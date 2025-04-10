package com.ilyeong.movieverse.presentation.search.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.presentation.search.viewholder.HeaderViewHolder

class HeaderAdapter : Adapter<HeaderViewHolder>() {
    private var title: String = ""

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

    @SuppressLint("NotifyDataSetChanged")
    fun updateHeaderTitle(title: String?) {
        this.title = title ?: ""
        notifyDataSetChanged()
    }
}