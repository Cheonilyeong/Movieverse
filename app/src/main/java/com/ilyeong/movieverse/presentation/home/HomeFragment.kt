package com.ilyeong.movieverse.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.MarginPageTransformer
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentHomeBinding
import com.ilyeong.movieverse.presentation.common.adapter.GenreAdapter
import com.ilyeong.movieverse.presentation.common.adapter.PosterFixedAdapter
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.home.adapter.PosterFullViewHolder
import com.ilyeong.movieverse.presentation.home.model.HomeEvent
import com.ilyeong.movieverse.presentation.home.model.HomeUiState
import com.ilyeong.movieverse.presentation.util.ItemClickListener
import com.ilyeong.movieverse.presentation.util.PosterFixedItemDecoration
import dagger.hilt.android.AndroidEntryPoint

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

    private val posterFullViewHolder = PosterFullViewHolder(movieClickListener)
    private val genreAdapter = GenreAdapter(genreClickListener)
    private val watchlistAdapter = PosterFixedAdapter(movieClickListener)
    private val topRatedAdapter = PosterFixedAdapter(movieClickListener)
    private val upcomingAdapter = PosterFixedAdapter(movieClickListener)
    private val popularAdapter = PosterFixedAdapter(movieClickListener)
    private val nowPlayingAdapter = PosterFixedAdapter(movieClickListener)
    private val trendingAdapter = PosterFixedAdapter(movieClickListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarMenu()
        setMovieBanner()
        setMovieGenre()
        setMovieSection()
        setRetryBtn()

        observeUiState()
        observeEvents()
        loadData()
    }

    private fun setToolbarMenu() {
        binding.tb.setOnMenuItemClickListener { _ ->
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
            true
        }
    }

    private fun setMovieBanner() {
        binding.vpBanner.adapter = posterFullViewHolder
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
            loadData()
        }
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    HomeUiState.Loading -> {
                        binding.sfl.startShimmer()
                        binding.sfl.isVisible = true
                        binding.content.isVisible = false
                        binding.ldf.root.isVisible = false
                    }

                    is HomeUiState.Success -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.content.isVisible = true
                        binding.ldf.root.isVisible = false

                        posterFullViewHolder.submitList(it.bannerMovieList)
                        genreAdapter.submitList(it.genreList)
                        watchlistAdapter.submitList(it.watchlistMovieList)
                        topRatedAdapter.submitList(it.topRatedMovieList)
                        upcomingAdapter.submitList(it.upcomingMovieList)
                        popularAdapter.submitList(it.popularMovieList)
                        nowPlayingAdapter.submitList(it.nowPlayingMovieList)
                        trendingAdapter.submitList(it.trendingWeekMovieList)

                        binding.tvMovieSection1.isVisible = it.watchlistMovieList.isNotEmpty()
                        binding.rvMovieSection1.isVisible = it.watchlistMovieList.isNotEmpty()
                    }

                    HomeUiState.Failure -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.content.isVisible = false
                        binding.ldf.root.isVisible = true
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        repeatOnViewStarted {
            viewModel.events.collect {
                when (it) {
                    is HomeEvent.ShowMessage -> showMessage(it.error.message.toString())
                }
            }
        }
    }

    private fun loadData() {
        viewModel.loadData()
    }
}