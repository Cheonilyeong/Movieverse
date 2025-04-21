package com.ilyeong.movieverse.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.viewpager2.widget.MarginPageTransformer
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentHomeBinding
import com.ilyeong.movieverse.presentation.common.adapter.GenreAdapter
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.home.adapter.PosterFixedPagingAdapter
import com.ilyeong.movieverse.presentation.home.adapter.PosterFullAdapter
import com.ilyeong.movieverse.presentation.home.model.HomeUiState
import com.ilyeong.movieverse.presentation.util.ItemClickListener
import com.ilyeong.movieverse.presentation.util.PosterFixedItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    private val movieClickListener = ItemClickListener { movieId ->
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private val genreClickListener = ItemClickListener { genreId ->
        val action = HomeFragmentDirections.actionHomeFragmentToGenreFragment(genreId)
        findNavController().navigate(action)
    }

    private val posterFullAdapter = PosterFullAdapter(movieClickListener)
    private val genreAdapter = GenreAdapter(genreClickListener)
    private val watchlistAdapter = PosterFixedPagingAdapter(movieClickListener)
    private val topRatedAdapter = PosterFixedPagingAdapter(movieClickListener)
    private val upcomingAdapter = PosterFixedPagingAdapter(movieClickListener)
    private val popularAdapter = PosterFixedPagingAdapter(movieClickListener)
    private val nowPlayingAdapter = PosterFixedPagingAdapter(movieClickListener)
    private val trendingAdapter = PosterFixedPagingAdapter(movieClickListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarMenu()
        setMovieBanner()
        setMovieGenre()
        setMovieSection()
        setRetryBtn()

        observeUiState()

        refreshData()
    }

    private fun setToolbarMenu() {
        binding.tb.setOnMenuItemClickListener { _ ->
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
            true
        }
    }

    private fun setMovieBanner() {
        binding.vpBanner.adapter = posterFullAdapter
        binding.vpBanner.offscreenPageLimit = 1
        binding.vpBanner.setPageTransformer(
            MarginPageTransformer(
                binding.root.context.resources.getDimensionPixelSize(
                    R.dimen.movieverse_padding_small
                )
            )
        )
    }

    private fun setMovieGenre() {
        binding.rvMovieGenre.adapter = genreAdapter
        binding.rvMovieGenre.addItemDecoration(PosterFixedItemDecoration)
    }

    private fun setMovieSection() {
        binding.tvMovieSection1.text = getString(R.string.movie_section_watchlist)
        binding.tvMovieSection2.text = getString(R.string.movie_section_upcoming)
        binding.tvMovieSection3.text = getString(R.string.movie_section_popular)
        binding.tvMovieSection4.text = getString(R.string.movie_section_now_playing)
        binding.tvMovieSection5.text = getString(R.string.movie_section_trending_week)
        binding.tvMovieSection6.text = getString(R.string.movie_section_top_rated)

        binding.rvMovieSection1.adapter = watchlistAdapter
        binding.rvMovieSection2.adapter = upcomingAdapter
        binding.rvMovieSection3.adapter = popularAdapter
        binding.rvMovieSection4.adapter = nowPlayingAdapter
        binding.rvMovieSection5.adapter = trendingAdapter
        binding.rvMovieSection6.adapter = topRatedAdapter

        binding.rvMovieSection1.addItemDecoration(PosterFixedItemDecoration)
        binding.rvMovieSection2.addItemDecoration(PosterFixedItemDecoration)
        binding.rvMovieSection3.addItemDecoration(PosterFixedItemDecoration)
        binding.rvMovieSection4.addItemDecoration(PosterFixedItemDecoration)
        binding.rvMovieSection5.addItemDecoration(PosterFixedItemDecoration)
        binding.rvMovieSection6.addItemDecoration(PosterFixedItemDecoration)
    }

    private fun setRetryBtn() {
        binding.ldf.btnRetry.setOnClickListener {
            viewModel.loadData()
            watchlistAdapter.retry()
            upcomingAdapter.retry()
            popularAdapter.retry()
            nowPlayingAdapter.retry()
            trendingAdapter.retry()
            topRatedAdapter.retry()
        }
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.watchlistPaging.collectLatest {
                watchlistAdapter.submitData(it)
            }
        }

        repeatOnViewStarted {
            viewModel.upcomingMoviePaging.collectLatest {
                upcomingAdapter.submitData(it)
            }
        }

        repeatOnViewStarted {
            viewModel.popularMoviePaging.collectLatest {
                popularAdapter.submitData(it)
            }
        }

        repeatOnViewStarted {
            viewModel.nowPlayingMoviePaging.collectLatest {
                nowPlayingAdapter.submitData(it)
            }
        }

        repeatOnViewStarted {
            viewModel.trendingWeekMoviePaging.collectLatest {
                trendingAdapter.submitData(it)
            }
        }

        repeatOnViewStarted {
            viewModel.topRatedMoviePaging.collectLatest {
                topRatedAdapter.submitData(it)
            }
        }

        binding.tvMovieSection1.isVisible = (watchlistAdapter.itemCount > 0)
        binding.rvMovieSection1.isVisible = (watchlistAdapter.itemCount > 0)

        repeatOnViewStarted {
            combine(
                viewModel.uiState,
                watchlistAdapter.loadStateFlow,
                upcomingAdapter.loadStateFlow,
                popularAdapter.loadStateFlow,
                nowPlayingAdapter.loadStateFlow,
                trendingAdapter.loadStateFlow,
                topRatedAdapter.loadStateFlow
            ) {
                val uiState = it[0] as HomeUiState
                val watchlistState = it[1] as CombinedLoadStates
                val upcomingState = it[2] as CombinedLoadStates
                val popularState = it[3] as CombinedLoadStates
                val nowPlayingState = it[4] as CombinedLoadStates
                val trendingState = it[5] as CombinedLoadStates
                val topRatedState = it[6] as CombinedLoadStates

                val isFirstLoading =
                    uiState is HomeUiState.Loading
                            || (watchlistState.refresh is LoadState.Loading && watchlistAdapter.itemCount == 0 && binding.rvMovieSection1.isVisible == true)
                            || (upcomingState.refresh is LoadState.Loading && upcomingAdapter.itemCount == 0)
                            || (popularState.refresh is LoadState.Loading && popularAdapter.itemCount == 0)
                            || (nowPlayingState.refresh is LoadState.Loading && nowPlayingAdapter.itemCount == 0)
                            || (trendingState.refresh is LoadState.Loading && trendingAdapter.itemCount == 0)
                            || (topRatedState.refresh is LoadState.Loading && topRatedAdapter.itemCount == 0)

                val isFirstLoadingSuccess =
                    uiState is HomeUiState.Success
                            && watchlistState.refresh is LoadState.NotLoading
                            && upcomingState.refresh is LoadState.NotLoading
                            && popularState.refresh is LoadState.NotLoading
                            && nowPlayingState.refresh is LoadState.NotLoading
                            && trendingState.refresh is LoadState.NotLoading
                            && topRatedState.refresh is LoadState.NotLoading

                val isFirstLoadingFailure =
                    uiState is HomeUiState.Failure
                            || (watchlistState.refresh is LoadState.Error && watchlistAdapter.itemCount == 0 && binding.rvMovieSection1.isVisible == false)
                            || (upcomingState.refresh is LoadState.Error && upcomingAdapter.itemCount == 0)
                            || (popularState.refresh is LoadState.Error && popularAdapter.itemCount == 0)
                            || (nowPlayingState.refresh is LoadState.Error && nowPlayingAdapter.itemCount == 0)
                            || (trendingState.refresh is LoadState.Error && trendingAdapter.itemCount == 0)
                            || (topRatedState.refresh is LoadState.Error && topRatedAdapter.itemCount == 0)

                when {
                    isFirstLoading -> {
                        binding.sfl.startShimmer()
                        binding.sfl.isVisible = true
                        binding.content.isVisible = false
                        binding.ldf.root.isVisible = false
                    }

                    isFirstLoadingSuccess -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.content.isVisible = true
                        binding.ldf.root.isVisible = false

                        posterFullAdapter.submitList(uiState.bannerMovieList)
                        genreAdapter.submitList(uiState.genreList)

                        binding.tvMovieSection1.isVisible = (watchlistAdapter.itemCount > 0)
                        binding.rvMovieSection1.isVisible = (watchlistAdapter.itemCount > 0)
                    }

                    isFirstLoadingFailure -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.content.isVisible = false
                        binding.ldf.root.isVisible = true
                    }

                    else -> {   // refresh
                        if (watchlistState.refresh is LoadState.Error
                            || upcomingState.refresh is LoadState.Error
                            || popularState.refresh is LoadState.Error
                            || nowPlayingState.refresh is LoadState.Error
                            || trendingState.refresh is LoadState.Error
                            || topRatedState.refresh is LoadState.Error
                        ) {
                            showMessage(getString(R.string.fail_refresh_message))
                        }
                    }
                }
            }.collect()
        }
    }

    private fun refreshData() {
        watchlistAdapter.refresh()
    }
}