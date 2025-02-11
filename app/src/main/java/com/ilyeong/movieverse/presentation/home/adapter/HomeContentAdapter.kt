package com.ilyeong.movieverse.presentation.home.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyeong.movieverse.presentation.home.model.HomeContent
import com.ilyeong.movieverse.presentation.home.viewholder.BannerContentViewHolder
import com.ilyeong.movieverse.presentation.home.viewholder.GenreContentViewHolder
import com.ilyeong.movieverse.presentation.home.viewholder.SectionContentViewHolder

class HomeContentAdapter(
    private val homeContentItemList: List<HomeContent>
) : Adapter<ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = when (viewType) {
        HomeContent.BANNER_CONTENT -> BannerContentViewHolder.Companion.create(parent)
        HomeContent.GENRE_CONTENT -> GenreContentViewHolder.Companion.create(parent)
        HomeContent.SECTION_CONTENT -> SectionContentViewHolder.Companion.create(parent)
        else -> throw IllegalArgumentException("Unknown view type: $viewType")
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        when (holder) {
            is BannerContentViewHolder -> holder.bind(homeContentItemList[position] as HomeContent.BannerContent)
            is GenreContentViewHolder -> holder.bind(homeContentItemList[position] as HomeContent.GenreContent)
            is SectionContentViewHolder -> holder.bind(homeContentItemList[position] as HomeContent.SectionContent)
            else -> Log.d("Home", "onBindViewHolder: else")
        }
    }

    override fun getItemCount() = homeContentItemList.size

    override fun getItemViewType(position: Int) = homeContentItemList[position].getViewType()
}