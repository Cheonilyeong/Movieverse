package com.ilyeong.movieverse.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil3.load
import coil3.request.crossfade
import com.google.android.material.tabs.TabLayoutMediator
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentDetailBinding
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.detail.adapter.DetailTabAdapter
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding =
        FragmentDetailBinding::inflate

    private val viewModel: DetailViewModel by viewModels()

    private val movieId: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData(movieId.movieId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolBarNavigationIcon()
        setMovieWatchlistIcon()
        setMovieTab()

        observeUiState()
    }

    private fun setToolBarNavigationIcon() {
        binding.tb.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setMovieWatchlistIcon() {
        binding.ivWatchlist.setOnClickListener {
            viewModel.addMovieToWatchlist()
        }
    }

    private fun setMovieTab() {
        binding.vpTab.adapter = DetailTabAdapter(this)
        binding.vpTab.setUserInputEnabled(false)

        TabLayoutMediator(binding.tl, binding.vpTab) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.information)
                1 -> tab.text = getString(R.string.recommended)
                2 -> tab.text = getString(R.string.review)
            }
        }.attach()
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    DetailUiState.Loading -> {
                        binding.sfl.startShimmer()
                        binding.sfl.isVisible = true
                        binding.content.isVisible = false
                    }

                    is DetailUiState.Success -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.content.isVisible = true

                        with(it.movie) {
                            binding.ivBackdrop.load(this.backdropPath) {
                                crossfade(true)
                            }

                            binding.posterDefault.ivPoster.load(this.posterPath) {
                                crossfade(true)
                                listener(
                                    onStart = { _ ->
                                        binding.posterDefault.tvPosterTitle.text = null
                                    },
                                    onError = { _, _ ->
                                        binding.posterDefault.tvPosterTitle.text = this@with.title
                                    }
                                )
                            }

                            binding.tvMovieTitle.text = this.title
                            binding.ivWatchlist.isSelected = this.isInWatchlist

                            binding.rrv.rating = this.voteAverage
                            binding.rrv.ratingCount = this.voteCount
                        }
                    }

                    DetailUiState.Failure -> {
                        // todo
                    }
                }
            }
        }
    }
}