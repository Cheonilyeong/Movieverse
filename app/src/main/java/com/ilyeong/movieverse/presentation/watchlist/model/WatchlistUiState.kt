package com.ilyeong.movieverse.presentation.watchlist.model

import com.ilyeong.movieverse.domain.model.Movie

sealed interface WatchlistUiState {
    data object Loading : WatchlistUiState

    data class Success(
        val watchlist: List<Movie>
    ) : WatchlistUiState

    data object Failure : WatchlistUiState
}