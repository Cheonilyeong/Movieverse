package com.ilyeong.movieverse.presentation.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.domain.model.Cast
import com.ilyeong.movieverse.presentation.detail.viewholder.CastViewHolder

class CastAdapter(private val castList: List<Cast>) : Adapter<CastViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CastViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: CastViewHolder,
        position: Int
    ) {
        holder.bind(castList[position])
    }

    override fun getItemCount() = castList.size
}