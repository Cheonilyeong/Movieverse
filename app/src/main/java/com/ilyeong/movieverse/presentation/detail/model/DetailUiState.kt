package com.ilyeong.movieverse.presentation.detail.model

import com.ilyeong.movieverse.domain.model.Cast
import com.ilyeong.movieverse.domain.model.Movie

sealed interface DetailUiState {

    data object Loading : DetailUiState

    data class Success(
        val movie: Movie,
        val cast: List<Cast>,
        val movieRecommendationList: List<Movie>,
        val movieSimilarList: List<Movie>,
    ) : DetailUiState

    data object Failure : DetailUiState
}