package com.ilyeong.movieverse.presentation.search.model

import com.ilyeong.movieverse.domain.model.Movie

sealed interface SearchUiState {
    data object Loading : SearchUiState
    data object EmptyPrompt : SearchUiState
    data class Trending(val movies: List<Movie>) : SearchUiState
}