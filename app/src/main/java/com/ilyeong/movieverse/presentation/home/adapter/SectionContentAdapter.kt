package com.ilyeong.movieverse.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.presentation.home.model.HomeContent.SectionContent
import com.ilyeong.movieverse.presentation.home.viewholder.SectionContentViewHolder

class SectionContentAdapter(
    private val sectionContent: SectionContent
) : Adapter<SectionContentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SectionContentViewHolder {
        return SectionContentViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: SectionContentViewHolder,
        position: Int
    ) {
        holder.bind(sectionContent)
    }

    override fun getItemCount() = 1
}