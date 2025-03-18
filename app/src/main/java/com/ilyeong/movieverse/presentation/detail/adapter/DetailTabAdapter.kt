package com.ilyeong.movieverse.presentation.detail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ilyeong.movieverse.presentation.detail.InformationFragment
import com.ilyeong.movieverse.presentation.detail.RecommendedFragment

class DetailTabAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {
    private val fragmentList = listOf(
        InformationFragment(),
        RecommendedFragment()
    )

    override fun createFragment(position: Int) = fragmentList[position]

    override fun getItemCount() = fragmentList.size
}