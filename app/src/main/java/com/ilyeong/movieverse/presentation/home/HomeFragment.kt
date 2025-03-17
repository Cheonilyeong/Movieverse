package com.ilyeong.movieverse.presentation.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.viewpager2.widget.MarginPageTransformer
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentHomeBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.home.adapter.BannerAdapter
import com.ilyeong.movieverse.presentation.home.adapter.GenreAdapter
import com.ilyeong.movieverse.presentation.home.adapter.SectionAdapter
import com.ilyeong.movieverse.presentation.home.model.HomeUiState
import com.ilyeong.movieverse.presentation.util.MovieClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    private val movieClickListener = MovieClickListener { movieId ->
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private val bannerAdapter = BannerAdapter(movieClickListener)
    private val genreAdapter = GenreAdapter()
    private val topRatedAdapter = SectionAdapter(movieClickListener)
    private val upcomingAdapter = SectionAdapter(movieClickListener)
    private val popularAdapter = SectionAdapter(movieClickListener)
    private val nowPlayingAdapter = SectionAdapter(movieClickListener)
    private val trendingAdapter = SectionAdapter(movieClickListener)
    private val watchlistAdapter = SectionAdapter(movieClickListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieBanner()
        setMovieGenre()
        setMovieSection()

        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    HomeUiState.Loading -> {}

                    is HomeUiState.Success -> {
                        bannerAdapter.submitList(it.bannerMovieList)
                        genreAdapter.submitList(it.genreList)
                        topRatedAdapter.submitList(it.topRatedMovieList)
                        upcomingAdapter.submitList(it.upcomingMovieList)
                        popularAdapter.submitList(it.popularMovieList)
                        nowPlayingAdapter.submitList(it.nowPlayingMovieList)
                        trendingAdapter.submitList(it.trendingWeekMovieList)
                        watchlistAdapter.submitList(it.watchlistMovieList)
                    }

                    HomeUiState.Failure -> {}
                }
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
        binding.rvMovieGenre.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val itemCount = state.itemCount
                val context = parent.context

                when (position) {
                    0 -> {
                        outRect.left =
                            context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                    }

                    (itemCount - 1) -> {
                        outRect.right =
                            context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                    }
                }
            }
        })
    }

    private fun setMovieSection() {
        binding.movieSection1.tvTitle.text = getString(R.string.movie_section_top_rated)
        binding.movieSection2.tvTitle.text = getString(R.string.movie_section_upcoming)
        binding.movieSection3.tvTitle.text = getString(R.string.movie_section_popular)
        binding.movieSection4.tvTitle.text = getString(R.string.movie_section_now_playing)
        binding.movieSection5.tvTitle.text = getString(R.string.movie_section_trending_week)
        binding.movieSection6.tvTitle.text = getString(R.string.movie_section_watchlist)

        val itemDecoration = object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val itemCount = state.itemCount
                val resources = parent.context.resources

                val smallPadding =
                    resources.getDimensionPixelOffset(R.dimen.movieverse_padding_small)
                val largePadding =
                    resources.getDimensionPixelOffset(R.dimen.movieverse_padding_large)

                outRect.top = smallPadding
                outRect.bottom = smallPadding
                outRect.left = smallPadding
                outRect.right = smallPadding

                when (position) {
                    0 -> {
                        outRect.left = largePadding
                        outRect.right = smallPadding
                    }

                    itemCount - 1 -> {
                        outRect.left = smallPadding
                        outRect.right = largePadding
                    }
                }
            }
        }

        binding.movieSection1.rvMovieList.adapter = topRatedAdapter
        binding.movieSection2.rvMovieList.adapter = upcomingAdapter
        binding.movieSection3.rvMovieList.adapter = popularAdapter
        binding.movieSection4.rvMovieList.adapter = nowPlayingAdapter
        binding.movieSection5.rvMovieList.adapter = trendingAdapter
        binding.movieSection6.rvMovieList.adapter = watchlistAdapter

        binding.movieSection1.rvMovieList.addItemDecoration(itemDecoration)
        binding.movieSection2.rvMovieList.addItemDecoration(itemDecoration)
        binding.movieSection3.rvMovieList.addItemDecoration(itemDecoration)
        binding.movieSection4.rvMovieList.addItemDecoration(itemDecoration)
        binding.movieSection5.rvMovieList.addItemDecoration(itemDecoration)
        binding.movieSection6.rvMovieList.addItemDecoration(itemDecoration)
    }
}