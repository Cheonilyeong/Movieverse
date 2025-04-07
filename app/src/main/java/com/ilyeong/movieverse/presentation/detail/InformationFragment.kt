package com.ilyeong.movieverse.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentInformationBinding
import com.ilyeong.movieverse.presentation.common.adapter.GenreAdapter
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.detail.adapter.CastAdapter
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
import com.ilyeong.movieverse.presentation.util.PosterFixedItemDecoration

class InformationFragment : BaseFragment<FragmentInformationBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInformationBinding =
        FragmentInformationBinding::inflate

    private val viewModel: DetailViewModel by viewModels({ requireParentFragment() })

    private val castAdapter = CastAdapter()
    private val genreAdapter = GenreAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOverview()
        setCast()
        setGenre()

        observeUiState()
    }

    private fun setOverview() {
        binding.tvOverview.setOnClickListener {
            when (binding.tvOverview.maxLines) {
                5 -> binding.tvOverview.maxLines = Int.MAX_VALUE
                else -> binding.tvOverview.maxLines = 5
            }
        }
    }

    private fun setCast() {
        binding.rvMovieCast.adapter = castAdapter
        binding.rvMovieCast.addItemDecoration(PosterFixedItemDecoration)
    }

    private fun setGenre() {
        binding.rvMovieGenre.adapter = genreAdapter
        binding.rvMovieGenre.addItemDecoration(PosterFixedItemDecoration)
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    DetailUiState.Loading -> {
                        /* no-op */
                    }

                    is DetailUiState.Success -> {
                        val movie = it.movie

                        // 줄거리
                        binding.tvOverview.text = when (movie.overview.isBlank()) {
                            true -> getString(R.string.info_empty)
                            false -> movie.overview
                        }

                        // 주요 등장인물
                        castAdapter.submitList(it.cast)
                        binding.rvMovieCast.isVisible = it.cast.isNotEmpty()
                        binding.tvMovieCastEmpty.isVisible = it.cast.isEmpty()

                        // 장르
                        genreAdapter.submitList(movie.genreList)
                        binding.rvMovieGenre.isVisible = movie.genreList.isNotEmpty()
                        binding.tvMovieGenreEmpty.isVisible = movie.genreList.isEmpty()

                        // 추가 정보
                        binding.tvRelease.text = when (movie.releaseDate.isBlank()) {
                            true -> getString(R.string.release_empty)
                            false -> getString(R.string.release, movie.releaseDate)
                        }

                        binding.tvRuntime.text = when {
                            movie.runtime == 0 -> getString(R.string.runtime_empty)
                            movie.runtime < 60 -> getString(R.string.runtime_short, movie.runtime)
                            else -> getString(
                                R.string.runtime_long,
                                movie.runtime / 60,
                                movie.runtime % 60
                            )
                        }

                        binding.tvLanguage.text = when (movie.spokenLanguageList.isEmpty()) {
                            true -> getString(R.string.language_empty)
                            false -> getString(
                                R.string.language,
                                movie.spokenLanguageList.joinToString { it.englishName }
                            )
                        }
                    }

                    DetailUiState.Failure -> {
                        /* no-op */
                    }
                }
            }
        }
    }
}