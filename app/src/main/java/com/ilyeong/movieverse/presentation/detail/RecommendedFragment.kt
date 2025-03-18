package com.ilyeong.movieverse.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ilyeong.movieverse.databinding.FragmentRecommendedBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment

class RecommendedFragment : BaseFragment<FragmentRecommendedBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecommendedBinding =
        FragmentRecommendedBinding::inflate

}