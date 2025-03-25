package com.ilyeong.movieverse.presentation.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentGenreBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.genre.model.GenreUiState
import com.ilyeong.movieverse.presentation.home.adapter.SectionAdapter
import com.ilyeong.movieverse.presentation.util.PosterDescriptionItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : BaseFragment<FragmentGenreBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGenreBinding =
        FragmentGenreBinding::inflate

    private val viewModel: GenreViewModel by viewModels()

    private val genreId: GenreFragmentArgs by navArgs()

    private val genreMovieAdapter = SectionAdapter { movieId ->
        val action = GenreFragmentDirections.actionGenreFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadData(genreId.genreId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setGenreMovie()

        observeUiState()
    }

    private fun setGenreMovie() {
        binding.rvGenreMovie.adapter = genreMovieAdapter
        binding.rvGenreMovie.layoutManager =
            GridLayoutManager(requireContext(), calculateSpanCount())
        binding.rvGenreMovie.addItemDecoration(PosterDescriptionItemDecoration)
    }

    private fun calculateSpanCount(): Int {
        val screenWidth = resources.displayMetrics.widthPixels
        val itemWidth = resources.getDimensionPixelSize(R.dimen.movieverse_poster_default_width)

        return (screenWidth / itemWidth)
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    GenreUiState.Loading -> {}
                    is GenreUiState.Success -> {
                        binding.tb.title = it.genre.name
                        genreMovieAdapter.submitList(it.movieList)
                    }

                    GenreUiState.Failure -> {}
                }
            }
        }
    }
}