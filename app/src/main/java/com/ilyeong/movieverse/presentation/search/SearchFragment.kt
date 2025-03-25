package com.ilyeong.movieverse.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentSearchBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.home.adapter.SectionAdapter
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

    private val trendHeaderAdapter = HeaderAdapter()
    private val trendAdapter = TrendAdapter()

    private val searchHeaderAdapter = HeaderAdapter()
    private val searchAdapter = SectionAdapter { movieId ->
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarNavigationIcon()
        setSearchView()
        setSearch()
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

    private fun setSearch() {
        val spanCount = calculateSpanCount()

        binding.rvSearch.adapter = ConcatAdapter(searchHeaderAdapter, searchAdapter)
        binding.rvSearch.layoutManager = GridLayoutManager(requireContext(), spanCount)
            .apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = if (position == 0) spanCount else 1
                }
            }

        binding.rvSearch.addItemDecoration(PosterDescriptionItemDecoration)
    }

    private fun calculateSpanCount(): Int {
        val screenWidth = resources.displayMetrics.widthPixels
        val itemWidth = resources.getDimensionPixelSize(R.dimen.movieverse_poster_default_width)

        return (screenWidth / itemWidth)
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    SearchUiState.Loading -> {}

                    is SearchUiState.Success -> {
                        when {
                            // 검색 X
                            it.searchMovieList == null -> {
                                trendHeaderAdapter.updateHeaderTitle("인기 급상승")
                                trendAdapter.submitList(it.trendingDayMovieList)

                                binding.rvTrend.isVisible = true
                                binding.rvSearch.isVisible = false
                            }

                            // 검색 결과 X
                            it.searchMovieList.isEmpty() -> {
                                searchHeaderAdapter.updateHeaderTitle("검색 결과가 없습니다.")
                                searchAdapter.submitList(it.searchMovieList)

                                binding.rvTrend.isVisible = false
                                binding.rvSearch.isVisible = true
                            }

                            // 검색 성공
                            else -> {
                                searchHeaderAdapter.updateHeaderTitle("검색 결과")
                                searchAdapter.submitList(it.searchMovieList)

                                binding.rvTrend.isVisible = false
                                binding.rvSearch.isVisible = true
                            }
                        }
                    }

                    SearchUiState.Failure -> {}
                }
            }
        }
    }
}