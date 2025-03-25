package com.ilyeong.movieverse.presentation.search.model

import com.ilyeong.movieverse.domain.model.Movie

sealed interface SearchUiState {
    data object Loading : SearchUiState

    data class Success(
        val trendingDayMovieList: List<Movie>,
        val searchMovieList: List<Movie>?,  // null(검색 X), empty(검색 결과 X), list(검색 결과 O)
    ) : SearchUiState

    data object Failure : SearchUiState
}