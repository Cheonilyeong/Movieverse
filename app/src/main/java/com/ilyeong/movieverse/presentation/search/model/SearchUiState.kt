package com.ilyeong.movieverse.presentation.search.model

import com.ilyeong.movieverse.domain.model.Movie

sealed interface SearchUiState {
    data object Loading : SearchUiState
    data class Success(val trendMovieList: List<Movie>) : SearchUiState
    data object Failure : SearchUiState
}