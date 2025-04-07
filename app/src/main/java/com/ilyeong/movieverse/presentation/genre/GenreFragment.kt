package com.ilyeong.movieverse.presentation.genre

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentGenreBinding
import com.ilyeong.movieverse.presentation.common.adapter.PosterRatioAdapter
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.genre.adapter.ShimmerPosterRatioAdapter
import com.ilyeong.movieverse.presentation.genre.model.GenreEvent.ShowMessage
import com.ilyeong.movieverse.presentation.genre.model.GenreUiState
import com.ilyeong.movieverse.presentation.util.calculateSpanCount
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : BaseFragment<FragmentGenreBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGenreBinding =
        FragmentGenreBinding::inflate

    private val viewModel: GenreViewModel by viewModels()

    private val genreId: GenreFragmentArgs by navArgs()

    private val genreMovieAdapter = PosterRatioAdapter { movieId ->
        val action = GenreFragmentDirections.actionGenreFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData(genreId.genreId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        setGenreMovie()
        setRetryBtn()

        observeUiState()
        observeEvents()
    }

    private fun setToolbar() {
        binding.tb.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setGenreMovie() {
        val spanCount = binding.rvGenreMovie.calculateSpanCount()
        val itemDecoration = object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                if (position == RecyclerView.NO_POSITION) return

                val layoutManager = parent.layoutManager as? GridLayoutManager ?: return
                val spanSizeLookup = layoutManager.spanSizeLookup
                val spanCount = layoutManager.spanCount
                val spanIndex = spanSizeLookup.getSpanIndex(position, spanCount)

                val mediumPadding =
                    resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                val largePadding =
                    resources.getDimensionPixelOffset(R.dimen.movieverse_padding_large)

                outRect.bottom = largePadding

                val third = (mediumPadding / 3f).toInt()
                when (spanIndex) {
                    0 -> {
                        outRect.right = third * 2
                    }

                    spanCount - 1 -> {
                        outRect.left = third * 2
                    }

                    else -> {
                        outRect.left = third
                        outRect.right = third
                    }
                }
            }
        }

        binding.rvGenreMovie.adapter = genreMovieAdapter
        binding.rvGenreMovie.layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.rvGenreMovie.addItemDecoration(itemDecoration)

        binding.rvShimmer.adapter = ShimmerPosterRatioAdapter(spanCount * 5)
        binding.rvShimmer.layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.rvShimmer.addItemDecoration(itemDecoration)
    }

    private fun setRetryBtn() {
        binding.ldf.btnRetry.setOnClickListener {
            viewModel.loadData(genreId.genreId)
        }
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    GenreUiState.Loading -> {
                        binding.sfl.startShimmer()
                        binding.sfl.isVisible = true
                        binding.rvGenreMovie.isVisible = false
                        binding.ldf.root.isVisible = false
                    }

                    is GenreUiState.Success -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.rvGenreMovie.isVisible = true
                        binding.ldf.root.isVisible = false

                        binding.tb.title = it.genre.name
                        genreMovieAdapter.submitList(it.movieList)
                    }

                    GenreUiState.Failure -> {
                        binding.sfl.stopShimmer()
                        binding.sfl.isVisible = false
                        binding.rvGenreMovie.isVisible = false
                        binding.ldf.root.isVisible = true
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        repeatOnViewStarted {
            viewModel.events.collect {
                when (it) {
                    is ShowMessage -> showMessage(it.error.message.toString())
                }
            }
        }
    }
}