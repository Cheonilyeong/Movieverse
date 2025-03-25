package com.ilyeong.movieverse.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.ilyeong.movieverse.databinding.FragmentSearchBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.search.adapter.HeaderAdapter
import com.ilyeong.movieverse.presentation.search.adapter.TrendAdapter
import com.ilyeong.movieverse.presentation.search.model.SearchUiState
import com.ilyeong.movieverse.presentation.util.PosterDescriptionItemDecoration
import com.ilyeong.movieverse.presentation.util.getQueryFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate

    private val viewModel: SearchViewModel by viewModels()

    private val trendHeaderAdapter = HeaderAdapter("인기 급상승")
    private val trendAdapter = TrendAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarNavigationIcon()
        setSearchView()
        setTrend()

        observeUiState()
    }

    private fun setToolbarNavigationIcon() {
        binding.tb.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    @OptIn(FlowPreview::class)
    private fun setSearchView() {
        repeatOnViewStarted {
            binding.sv.getQueryFlow()
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest { query ->
                    Log.d("search", query)
                    viewModel.searchMovie(query)
                }
        }
    }

    private fun setTrend() {
        binding.rvTrend.adapter = ConcatAdapter(trendHeaderAdapter, trendAdapter)
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