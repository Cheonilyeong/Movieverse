package com.ilyeong.movieverse.presentation.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ilyeong.movieverse.databinding.FragmentGenreBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : BaseFragment<FragmentGenreBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGenreBinding =
        FragmentGenreBinding::inflate

    private val viewModel: GenreViewModel by viewModels()

}