package com.ilyeong.movieverse.presentation.detail.model

import com.ilyeong.movieverse.domain.model.Movie

sealed interface DetailUiState {

    data object Loading : DetailUiState

    data class Success(val movie: Movie) : DetailUiState

    data object Failure : DetailUiState
}