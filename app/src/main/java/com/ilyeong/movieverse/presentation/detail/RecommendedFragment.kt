package com.ilyeong.movieverse.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentRecommendedBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
import com.ilyeong.movieverse.presentation.home.adapter.SectionAdapter
import com.ilyeong.movieverse.presentation.util.MovieClickListener
import com.ilyeong.movieverse.presentation.util.MovieverseItemDecoration

class RecommendedFragment : BaseFragment<FragmentRecommendedBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecommendedBinding =
        FragmentRecommendedBinding::inflate

    private val viewModel: DetailViewModel by viewModels({ requireParentFragment() })

    private val movieClickListener = MovieClickListener { movieId ->
        val action = DetailFragmentDirections.actionDetailFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private val collectionAdapter = SectionAdapter(movieClickListener)
    private val recommendationAdapter = SectionAdapter(movieClickListener)
    private val similarAdapter = SectionAdapter(movieClickListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    is DetailUiState.Loading -> {
                        // todo
                    }

                    is DetailUiState.Success -> {
                        binding.movieSection1.tvTitle.isVisible =
                            it.collectionMovieList.isEmpty().not()
                        binding.movieSection1.rvMovieList.isVisible =
                            it.collectionMovieList.isEmpty().not()

                        collectionAdapter.submitList(it.collectionMovieList)
                        recommendationAdapter.submitList(it.movieRecommendationList)
                        similarAdapter.submitList(it.movieSimilarList)
                    }

                    is DetailUiState.Failure -> {
                        // todo
                    }
                }
            }
        }
    }

    private fun setAdapter() {
        binding.movieSection1.tvTitle.text = getString(R.string.movie_section_collection)
        binding.movieSection2.tvTitle.text = getString(R.string.movie_section_recommendation)
        binding.movieSection3.tvTitle.text = getString(R.string.movie_section_similar)

        binding.movieSection1.rvMovieList.adapter = collectionAdapter
        binding.movieSection2.rvMovieList.adapter = recommendationAdapter
        binding.movieSection3.rvMovieList.adapter = similarAdapter

        binding.movieSection1.rvMovieList.addItemDecoration(MovieverseItemDecoration)
        binding.movieSection2.rvMovieList.addItemDecoration(MovieverseItemDecoration)
        binding.movieSection3.rvMovieList.addItemDecoration(MovieverseItemDecoration)
    }
}