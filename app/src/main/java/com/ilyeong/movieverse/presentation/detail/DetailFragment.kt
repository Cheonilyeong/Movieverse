package com.ilyeong.movieverse.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil3.load
import coil3.request.crossfade
import com.ilyeong.movieverse.databinding.FragmentDetailBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}