package com.ilyeong.movieverse.presentation.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ilyeong.movieverse.databinding.FragmentWatchlistBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.util.PosterDescriptionItemDecoration
import com.ilyeong.movieverse.presentation.watchlist.adapter.WatchlistAdapter
import com.ilyeong.movieverse.presentation.watchlist.model.WatchlistUiState.Failure
import com.ilyeong.movieverse.presentation.watchlist.model.WatchlistUiState.Loading
import com.ilyeong.movieverse.presentation.watchlist.model.WatchlistUiState.Success
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchlistFragment : BaseFragment<FragmentWatchlistBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWatchlistBinding
        get() = FragmentWatchlistBinding::inflate

    private val viewModel: WatchlistViewModel by viewModels()

    private val watchlistAdapter = WatchlistAdapter { movieId ->
        val action = WatchlistFragmentDirections.actionWatchlistFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWatchlist()

        observeUiState()
        loadData()
    }

    private fun setWatchlist() {
        binding.rvWatchlist.adapter = watchlistAdapter
        binding.rvWatchlist.addItemDecoration(PosterDescriptionItemDecoration)
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    is Loading -> {
                        binding.sfl.startShimmer()
                        binding.sfl.isVisible = true
                        binding.content.isVisible = false
                    }

                    is Success -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.content.isVisible = true

                        watchlistAdapter.submitList(it.watchlist)
                        binding.tvWatchlistEmpty.isVisible = it.watchlist.isEmpty()
                    }

                    Failure -> {}
                }
            }
        }
    }

    private fun loadData() {
        viewModel.loadData()
    }
}