package com.ilyeong.movieverse.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ilyeong.movieverse.databinding.FragmentHomeBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
}