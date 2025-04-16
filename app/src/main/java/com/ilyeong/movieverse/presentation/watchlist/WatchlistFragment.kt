package com.ilyeong.movieverse.presentation.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.ilyeong.movieverse.databinding.FragmentWatchlistBinding
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.util.PosterDescriptionItemDecoration
import com.ilyeong.movieverse.presentation.watchlist.adapter.PosterDescriptionPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class WatchlistFragment : BaseFragment<FragmentWatchlistBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWatchlistBinding
        get() = FragmentWatchlistBinding::inflate

    private val viewModel: WatchlistViewModel by viewModels()

    private val watchlistAdapter = PosterDescriptionPagingAdapter { movieId ->
        val action = WatchlistFragmentDirections.actionWatchlistFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWatchlist()
        setRetryBtn()

        observeWatchlist()

        refreshData()
    }

    private fun setWatchlist() {
        binding.rvWatchlist.adapter = watchlistAdapter
        binding.rvWatchlist.addItemDecoration(PosterDescriptionItemDecoration)
    }

    private fun setRetryBtn() {
        binding.ldf.btnRetry.setOnClickListener {
            watchlistAdapter.retry()
        }
    }

    private fun observeWatchlist() {
        repeatOnViewStarted {
            viewModel.watchlistPaging.collectLatest {
                watchlistAdapter.submitData(it)
            }
        }

        repeatOnViewStarted {
            watchlistAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        // 최초 로딩일 때만 Shimmer
                        if (watchlistAdapter.itemCount == 0) {
                            binding.sfl.startShimmer()
                            binding.sfl.isVisible = true
                            binding.content.isVisible = false
                            binding.ldf.root.isVisible = false
                        }
                    }

                    is LoadState.NotLoading -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.content.isVisible = true
                        binding.ldf.root.isVisible = false

                        binding.tvWatchlistEmpty.isVisible = (watchlistAdapter.itemCount == 0)
                    }

                    is LoadState.Error -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.content.isVisible = false
                        binding.ldf.root.isVisible = true
                    }
                }
            }
        }
    }

    private fun refreshData() {
        watchlistAdapter.refresh()
    }
}