package com.ilyeong.movieverse.presentation.watchlist

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentWatchlistBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.util.MovieClickListener
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

    private val movieClickListener = MovieClickListener { movieId ->
        val action = WatchlistFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvWatchlist.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom =
                    resources.getDimensionPixelOffset(R.dimen.movieverse_padding_xlarge)
            }
        })

        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    Loading -> {}

                    is Success -> {
                        binding.rvWatchlist.adapter =
                            WatchlistAdapter(it.watchlist, movieClickListener)
                    }

                    Failure -> {}
                }
            }
        }
    }
}