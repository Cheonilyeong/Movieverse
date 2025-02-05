package com.ilyeong.movieverse.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ilyeong.movieverse.databinding.FragmentHomeBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpBanner.offscreenPageLimit = 1
        binding.vpBanner.adapter = BannerAdapter()
    }
}