package com.ilyeong.movieverse.presentation.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.MarginPageTransformer
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.ItemMovieBannerBinding
import com.ilyeong.movieverse.presentation.home.adapter.BannerContentAdapter
import com.ilyeong.movieverse.presentation.home.model.HomeContent.BannerContent

class BannerContentViewHolder private constructor(
    private val binding: ItemMovieBannerBinding
) : ViewHolder(binding.root) {

    fun bind(bannerContent: BannerContent) {
        binding.vpBanner.offscreenPageLimit = 1
        binding.vpBanner.adapter = BannerContentAdapter(bannerContent)
        binding.vpBanner.setPageTransformer(
            MarginPageTransformer(
                binding.root.context.resources.getDimensionPixelSize(
                    R.dimen.movieverse_padding_small
                )
            )
        )
    }

    companion object {
        fun create(parent: ViewGroup): ViewHolder {
            val binding =
                ItemMovieBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return BannerContentViewHolder(binding)
        }
    }
}