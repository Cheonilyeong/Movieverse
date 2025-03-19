package com.ilyeong.movieverse.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ilyeong.movieverse.databinding.FragmentReviewBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment
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
                        //todo
                    }

                    is DetailUiState.Success -> {
                        reviewAdapter.submitList(it.movieReviewList)
                    }

                    is DetailUiState.Failure -> {
                        //todo
                    }
                }
            }
        }
    }
}