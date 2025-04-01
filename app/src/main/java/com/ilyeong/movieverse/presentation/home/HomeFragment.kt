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
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.home.adapter.BannerAdapter
import com.ilyeong.movieverse.presentation.home.adapter.GenreAdapter
import com.ilyeong.movieverse.presentation.home.adapter.SectionAdapter
import com.ilyeong.movieverse.presentation.home.model.HomeUiState
import com.ilyeong.movieverse.presentation.util.ItemClickListener
import com.ilyeong.movieverse.presentation.util.PosterDefaultItemDecoration
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

    private val bannerAdapter = BannerAdapter(movieClickListener)
    private val genreAdapter = GenreAdapter(genreClickListener)
    private val watchlistAdapter = SectionAdapter(movieClickListener)
    private val topRatedAdapter = SectionAdapter(movieClickListener)
    private val upcomingAdapter = SectionAdapter(movieClickListener)
    private val popularAdapter = SectionAdapter(movieClickListener)
    private val nowPlayingAdapter = SectionAdapter(movieClickListener)
    private val trendingAdapter = SectionAdapter(movieClickListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarMenu()
        setMovieBanner()
        setMovieGenre()
        setMovieSection()

        observeUiState()
        loadData()
    }

    private fun setToolbarMenu() {
        binding.tb.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.search -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                    findNavController().navigate(action)
                    true
                }

                else -> false
            }
        }
    }

    private fun setMovieBanner() {
        binding.vpBanner.adapter = bannerAdapter
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
        binding.rvMovieGenre.addItemDecoration(PosterDefaultItemDecoration)
    }

    private fun setMovieSection() {
        binding.movieSection1.tvTitle.text = getString(R.string.movie_section_watchlist)
        binding.movieSection2.tvTitle.text = getString(R.string.movie_section_upcoming)
        binding.movieSection3.tvTitle.text = getString(R.string.movie_section_popular)
        binding.movieSection4.tvTitle.text = getString(R.string.movie_section_now_playing)
        binding.movieSection5.tvTitle.text = getString(R.string.movie_section_trending_week)
        binding.movieSection6.tvTitle.text = getString(R.string.movie_section_top_rated)

        binding.movieSection1.rvMovieList.adapter = watchlistAdapter
        binding.movieSection2.rvMovieList.adapter = upcomingAdapter
        binding.movieSection3.rvMovieList.adapter = popularAdapter
        binding.movieSection4.rvMovieList.adapter = nowPlayingAdapter
        binding.movieSection5.rvMovieList.adapter = trendingAdapter
        binding.movieSection6.rvMovieList.adapter = topRatedAdapter

        binding.movieSection1.rvMovieList.addItemDecoration(PosterDefaultItemDecoration)
        binding.movieSection2.rvMovieList.addItemDecoration(PosterDefaultItemDecoration)
        binding.movieSection3.rvMovieList.addItemDecoration(PosterDefaultItemDecoration)
        binding.movieSection4.rvMovieList.addItemDecoration(PosterDefaultItemDecoration)
        binding.movieSection5.rvMovieList.addItemDecoration(PosterDefaultItemDecoration)
        binding.movieSection6.rvMovieList.addItemDecoration(PosterDefaultItemDecoration)
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    HomeUiState.Loading -> {}

                    is HomeUiState.Success -> {
                        bannerAdapter.submitList(it.bannerMovieList)
                        genreAdapter.submitList(it.genreList)
                        watchlistAdapter.submitList(it.watchlistMovieList)
                        topRatedAdapter.submitList(it.topRatedMovieList)
                        upcomingAdapter.submitList(it.upcomingMovieList)
                        popularAdapter.submitList(it.popularMovieList)
                        nowPlayingAdapter.submitList(it.nowPlayingMovieList)
                        trendingAdapter.submitList(it.trendingWeekMovieList)

                        binding.movieSection1.root.isVisible = it.watchlistMovieList.isNotEmpty()
                    }

                    HomeUiState.Failure -> {}
                }
            }
        }
    }

    private fun loadData() {
        viewModel.loadData()
    }
}