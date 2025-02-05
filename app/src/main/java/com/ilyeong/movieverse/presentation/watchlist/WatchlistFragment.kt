package com.ilyeong.movieverse.presentation.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ilyeong.movieverse.databinding.FragmentWatchlistBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment

class WatchlistFragment : BaseFragment<FragmentWatchlistBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWatchlistBinding
        get() = FragmentWatchlistBinding::inflate
}