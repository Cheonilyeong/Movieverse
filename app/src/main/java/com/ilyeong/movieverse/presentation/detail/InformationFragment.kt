package com.ilyeong.movieverse.presentation.detail

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentInformationBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.detail.adapter.CastAdapter
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
import com.ilyeong.movieverse.presentation.home.adapter.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInformationBinding =
        FragmentInformationBinding::inflate

    private val viewModel: DetailViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovieCast.addItemDecoration(object : ItemDecoration() {
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

        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    DetailUiState.Loading -> {
                        // todo
                    }

                    is DetailUiState.Success -> {
                        val movie = it.movie

                        binding.tvOverview.text = movie.overview

                        binding.rvMovieCast.adapter = CastAdapter(it.cast)

                        val genreAdapter = GenreAdapter()
                        binding.rvMovieGenre.adapter = genreAdapter
                        genreAdapter.submitList(movie.genreList)

                        binding.tvRelease.text = getString(R.string.release, movie.releaseDate)
                        when (movie.runtime) {
                            in (0..59) -> {
                                binding.tvRuntime.text =
                                    getString(R.string.runtime_short, movie.runtime)
                            }

                            else -> {
                                binding.tvRuntime.text = getString(
                                    R.string.runtime_long,
                                    movie.runtime / 60,
                                    movie.runtime % 60
                                )
                            }
                        }

                        binding.tvLanguage.text =
                            getString(R.string.language, movie.originalLanguage)
                    }

                    DetailUiState.Failure -> {
                        // todo
                    }
                }
            }
        }
    }
}