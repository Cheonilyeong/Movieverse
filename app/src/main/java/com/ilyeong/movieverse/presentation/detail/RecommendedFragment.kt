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

        setCollection()
        setRecommendation()
        setSimilar()

        observeUiState()
    }

    private fun setCollection() {
        binding.movieSection1.tvTitle.text = getString(R.string.movie_section_collection)
        binding.movieSection1.rvMovieList.adapter = collectionAdapter
        binding.movieSection1.rvMovieList.addItemDecoration(MovieverseItemDecoration)
    }

    private fun setRecommendation() {
        binding.movieSection2.tvTitle.text = getString(R.string.movie_section_recommendation)
        binding.movieSection2.rvMovieList.adapter = recommendationAdapter
        binding.movieSection2.rvMovieList.addItemDecoration(MovieverseItemDecoration)
    }

    private fun setSimilar() {
        binding.movieSection3.tvTitle.text = getString(R.string.movie_section_similar)
        binding.movieSection3.rvMovieList.adapter = similarAdapter
        binding.movieSection3.rvMovieList.addItemDecoration(MovieverseItemDecoration)
    }

    private fun observeUiState() {
        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    is DetailUiState.Loading -> {
                        /* no-op */
                    }

                    is DetailUiState.Success -> {
                        // 시리즈 영화
                        collectionAdapter.submitList(it.collectionMovieList)
                        binding.movieSection1.tvTitle.isVisible =
                            it.collectionMovieList.isEmpty().not()
                        binding.movieSection1.rvMovieList.isVisible =
                            it.collectionMovieList.isEmpty().not()

                        // 추천 영화
                        recommendationAdapter.submitList(it.movieRecommendationList)
                        binding.movieSection2.tvTitle.isVisible =
                            it.movieRecommendationList.isEmpty().not()
                        binding.movieSection2.rvMovieList.isVisible =
                            it.movieRecommendationList.isEmpty().not()

                        // 관련 영화
                        similarAdapter.submitList(it.movieSimilarList)
                        binding.movieSection3.tvTitle.isVisible =
                            it.movieSimilarList.isEmpty().not()
                        binding.movieSection3.rvMovieList.isVisible =
                            it.movieSimilarList.isEmpty().not()

                        // 빈 화면
                        binding.tvReviewEmpty.isVisible =
                            it.collectionMovieList.isEmpty()
                                    && it.movieRecommendationList.isEmpty()
                                    && it.movieSimilarList.isEmpty()
                    }

                    is DetailUiState.Failure -> {
                        /* no-op */
                    }
                }
            }
        }
    }
}