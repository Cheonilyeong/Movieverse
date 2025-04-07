package com.ilyeong.movieverse.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.ilyeong.movieverse.databinding.FragmentReviewBinding
import com.ilyeong.movieverse.presentation.common.fragment.BaseFragment
import com.ilyeong.movieverse.presentation.detail.adapter.ReviewAdapter
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentReviewBinding =
        FragmentReviewBinding::inflate

    private val viewModel: DetailViewModel by viewModels({ requireParentFragment() })

    private val reviewAdapter = ReviewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvReview.adapter = reviewAdapter

        repeatOnViewStarted {
            viewModel.uiState.collect {
                when (it) {
                    is DetailUiState.Loading -> {
                        /* no-op */
                    }

                    is DetailUiState.Success -> {
                        reviewAdapter.submitList(it.movieReviewList)
                        binding.tvReviewEmpty.isVisible = it.movieReviewList.isEmpty()
                    }

                    is DetailUiState.Failure -> {
                        /* no-op */
                    }
                }
            }
        }
    }
}