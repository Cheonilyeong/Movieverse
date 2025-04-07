package com.ilyeong.movieverse.presentation.search

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentSearchBinding
import com.ilyeong.movieverse.presentation.common.adapter.PosterDescriptionAdapter
import com.ilyeong.movieverse.presentation.common.adapter.PosterRatioAdapter
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.search.adapter.HeaderAdapter
import com.ilyeong.movieverse.presentation.search.model.SearchUiState
import com.ilyeong.movieverse.presentation.util.ItemClickListener
import com.ilyeong.movieverse.presentation.util.PosterDescriptionItemDecoration
import com.ilyeong.movieverse.presentation.util.calculateSpanCount
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

    val itemClickListener = ItemClickListener { movieId ->
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private val trendHeaderAdapter = HeaderAdapter()
    private val posterDescriptionAdapter = PosterDescriptionAdapter(itemClickListener)

    private val searchHeaderAdapter = HeaderAdapter()
    private val searchAdapter = PosterRatioAdapter(itemClickListener)

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
                    viewModel.searchMovie(query)
                }
        }
    }

    private fun setTrend() {
        binding.rvTrend.adapter = ConcatAdapter(trendHeaderAdapter, posterDescriptionAdapter)
        binding.rvTrend.addItemDecoration(PosterDescriptionItemDecoration)
    }

    private fun setSearch() {
        val spanCount = binding.rvSearch.calculateSpanCount()

        binding.rvSearch.adapter = ConcatAdapter(searchHeaderAdapter, searchAdapter)
        binding.rvSearch.layoutManager = GridLayoutManager(requireContext(), spanCount)
            .apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) = if (position == 0) spanCount else 1
                }
            }

        binding.rvSearch.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                if (position == RecyclerView.NO_POSITION) return

                val layoutManager = parent.layoutManager as? GridLayoutManager ?: return
                val spanSizeLookup = layoutManager.spanSizeLookup
                val spanCount = layoutManager.spanCount
                val spanIndex = spanSizeLookup.getSpanIndex(position, spanCount)

                val resources = parent.context.resources
                val mediumPadding =
                    resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                val largePadding =
                    resources.getDimensionPixelOffset(R.dimen.movieverse_padding_large)

                outRect.bottom = largePadding

                if (position == 0) return

                val third = (mediumPadding / 3f).toInt()
                when (spanIndex) {
                    0 -> {
                        outRect.right = third * 2
                    }

                    spanCount - 1 -> {
                        outRect.left = third * 2
                    }

                    else -> {
                        outRect.left = third
                        outRect.right = third
                    }
                }
            }

        })
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
                    is SearchUiState.Loading -> {
                        binding.sfl.startShimmer()
                        binding.sfl.isVisible = true
                        binding.content.isVisible = false
                    }

                    is SearchUiState.Success -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.content.isVisible = true

                        when {
                            // 검색 X
                            it.searchMovieList == null -> {
                                trendHeaderAdapter.updateHeaderTitle(getString(R.string.movie_section_trending_day))
                                posterDescriptionAdapter.submitList(it.trendingDayMovieList)

                                binding.rvTrend.isVisible = true
                                binding.rvSearch.isVisible = false
                            }

                            // 검색 결과 X
                            it.searchMovieList.isEmpty() -> {
                                searchHeaderAdapter.updateHeaderTitle(getString(R.string.search_result_empty))
                                searchAdapter.submitList(it.searchMovieList)

                                binding.rvTrend.isVisible = false
                                binding.rvSearch.isVisible = true
                            }

                            // 검색 성공
                            else -> {
                                searchHeaderAdapter.updateHeaderTitle(getString(R.string.search_result))
                                searchAdapter.submitList(it.searchMovieList)

                                binding.rvTrend.isVisible = false
                                binding.rvSearch.isVisible = true
                            }
                        }
                    }

                    is SearchUiState.Failure -> {}
                }
            }
        }
    }
}