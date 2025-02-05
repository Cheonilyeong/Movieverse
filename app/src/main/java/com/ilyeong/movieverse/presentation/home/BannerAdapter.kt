package com.ilyeong.movieverse.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilyeong.movieverse.databinding.ItemBannerMovieBinding
import com.ilyeong.movieverse.presentation.home.BannerAdapter.BannerViewHolder

class BannerAdapter : Adapter<BannerViewHolder>() {

    class BannerViewHolder private constructor(private val binding: ItemBannerMovieBinding) :
        ViewHolder(binding.root) {

        fun onBind(position: Int) {
            binding.tvNum.text = "$position"
        }

        companion object {
            fun create(parent: ViewGroup): BannerViewHolder {
                val binding = ItemBannerMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return BannerViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewHolder {
        return BannerViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: BannerViewHolder,
        position: Int
    ) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return 10
    }
}