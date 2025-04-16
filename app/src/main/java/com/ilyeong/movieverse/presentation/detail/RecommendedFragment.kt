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
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.detail.adapter.PosterFixedAdapter
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
import com.ilyeong.movieverse.presentation.util.ItemClickListener
import com.ilyeong.movieverse.presentation.util.PosterFixedItemDecoration

class RecommendedFragment : BaseFragment<FragmentRecommendedBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecommendedBinding =
        FragmentRecommendedBinding::inflate

    private val viewModel: DetailViewModel by viewModels({ requireParentFragment() })

    private val itemClickListener = ItemClickListener { movieId ->
        val action = DetailFragmentDirections.actionDetailFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private val collectionAdapter = PosterFixedAdapter(itemClickListener)
    private val recommendationAdapter = PosterFixedAdapter(itemClickListener)
    private val similarAdapter = PosterFixedAdapter(itemClickListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollection()
        setRecommendation()
        setSimilar()

        observeUiState()
    }

    private fun setCollection() {
        binding.tvMovieSection1.text = getString(R.string.movie_section_collection)
        binding.rvMovieSection1.adapter = collectionAdapter
        binding.rvMovieSection1.addItemDecoration(PosterFixedItemDecoration)
    }

    private fun setRecommendation() {
        binding.tvMovieSection2.text = getString(R.string.movie_section_recommendation)
        binding.rvMovieSection2.adapter = recommendationAdapter
        binding.rvMovieSection2.addItemDecoration(PosterFixedItemDecoration)
    }

    private fun setSimilar() {
        binding.tvMovieSection3.text = getString(R.string.movie_section_similar)
        binding.rvMovieSection3.adapter = similarAdapter
        binding.rvMovieSection3.addItemDecoration(PosterFixedItemDecoration)
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
                        binding.tvMovieSection1.isVisible = it.collectionMovieList.isNotEmpty()
                        binding.rvMovieSection1.isVisible = it.collectionMovieList.isNotEmpty()

                        // 추천 영화
                        recommendationAdapter.submitList(it.movieRecommendationList)
                        binding.tvMovieSection2.isVisible = it.movieRecommendationList.isNotEmpty()
                        binding.rvMovieSection2.isVisible = it.movieRecommendationList.isNotEmpty()

                        // 관련 영화
                        similarAdapter.submitList(it.movieSimilarList)
                        binding.tvMovieSection3.isVisible = it.movieSimilarList.isNotEmpty()
                        binding.rvMovieSection3.isVisible = it.movieSimilarList.isNotEmpty()

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