package com.ilyeong.movieverse.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil3.load
import coil3.request.crossfade
import com.google.android.material.tabs.TabLayoutMediator
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentDetailBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
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
        setMovieTab()

        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    DetailUiState.Loading -> {
                        // todo
                    }

                    is DetailUiState.Success -> {
                        val movie = it.movie

                        binding.ivBackdrop.load(movie.backdropPath) {
                            crossfade(true)
                        }

                        binding.posterDefault.ivPoster.load(movie.posterPath) {
                            crossfade(true)
                            listener(
                                onStart = { _ -> binding.posterDefault.tvPosterTitle.text = null },
                                onError = { _, _ ->
                                    binding.posterDefault.tvPosterTitle.text = movie.title
                                }
                            )
                        }

                        binding.tvMovieTitle.text = movie.title

                        binding.rrv.rating = movie.voteAverage
                        binding.rrv.ratingCount = movie.voteCount
                    }

                    DetailUiState.Failure -> {
                        // todo
                    }
                }
            }
        }
    }

    private fun setToolBarNavigationIcon() {
        binding.tb.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setMovieTab() {
        binding.vpTab.adapter = DetailTabAdapter(this)
        binding.vpTab.setUserInputEnabled(false)

        TabLayoutMediator(binding.tl, binding.vpTab) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.information)
                1 -> tab.text = getString(R.string.recommended)
            }
        }.attach()
    }
}