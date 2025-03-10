package com.ilyeong.movieverse.presentation.home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    val bannerAdapter = BannerAdapter()
    val genreAdapter = GenreAdapter()
    val topRatedAdapter = SectionAdapter()
    val upcomingAdapter = SectionAdapter()
    val popularAdapter = SectionAdapter()
    val nowPlayingAdapter = SectionAdapter()
    val trendingAdapter = SectionAdapter()
    val watchlistAdapter = SectionAdapter()

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
                super.getItemOffsets(outRect, view, parent, state)

                val position = parent.getChildAdapterPosition(view)
                val itemCount = state.itemCount
                val context = parent.context

                if (position == 0) {
                    outRect.left =
                        context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                } else if (position == itemCount - 1) {
                    outRect.right =
                        context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                }
            }
        })
    }

    private fun setMovieSection() {
        binding.movieSection1.tvTitle.text = "Top Rated"
        binding.movieSection2.tvTitle.text = "UpComing"
        binding.movieSection3.tvTitle.text = "Popular"
        binding.movieSection4.tvTitle.text = "Now Playing"
        binding.movieSection5.tvTitle.text = "Trending Week"
        binding.movieSection6.tvTitle.text = "Watchlist"

        val itemDecoration = object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                val position = parent.getChildAdapterPosition(view)
                val itemCount = state.itemCount
                val context = parent.context

                val smallPadding =
                    context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_small)
                val largePadding =
                    context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_large)

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