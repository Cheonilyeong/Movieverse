package com.ilyeong.movieverse.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ilyeong.movieverse.databinding.FragmentSearchBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.search.adapter.TrendAdapter
import com.ilyeong.movieverse.presentation.search.model.SearchUiState
import com.ilyeong.movieverse.presentation.util.PosterDescriptionItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate

    private val viewModel: SearchViewModel by viewModels()

    private val trendAdapter = TrendAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarNavigationIcon()
        setTrend()

        observeUiState()
    }

    private fun setToolbarNavigationIcon() {
        binding.tb.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setTrend() {
        binding.rvTrend.adapter = trendAdapter
        binding.rvTrend.addItemDecoration(PosterDescriptionItemDecoration)
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    SearchUiState.Loading -> {}
                    is SearchUiState.Success -> {
                        trendAdapter.submitList(it.trendingDayMovieList)
                    }

                    SearchUiState.Failure -> {}
                }
            }
        }
    }
}