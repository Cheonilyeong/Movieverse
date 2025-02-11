package com.ilyeong.movieverse.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ilyeong.movieverse.presentation.home.model.HomeContent.BannerContent
import com.ilyeong.movieverse.presentation.home.viewholder.PosterFullViewHolder

class BannerContentAdapter(
    private val bannerContent: BannerContent,
) : Adapter<PosterFullViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PosterFullViewHolder {
        return PosterFullViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: PosterFullViewHolder,
        position: Int
    ) {
        holder.bind(bannerContent.movieList[position])
    }

    override fun getItemCount() = bannerContent.movieList.size
}