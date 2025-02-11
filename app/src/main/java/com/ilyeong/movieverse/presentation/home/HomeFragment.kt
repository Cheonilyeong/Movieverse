package com.ilyeong.movieverse.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ilyeong.movieverse.databinding.FragmentHomeBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.home.adapter.HomeContentAdapter
import com.ilyeong.movieverse.presentation.home.model.HomeUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    HomeUiState.Loading -> {}

                    is HomeUiState.Success -> {
                        binding.rvHomeContent.adapter = HomeContentAdapter(it.homeContentItemList)
                    }

                    HomeUiState.Failure -> {}
                }
            }
        }
    }
}