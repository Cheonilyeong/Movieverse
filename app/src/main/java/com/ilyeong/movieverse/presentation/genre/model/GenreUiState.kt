package com.ilyeong.movieverse.presentation.genre.model

import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.domain.model.Movie

sealed interface GenreUiState {
    data object Loading : GenreUiState

    data class Success(
        val genre: Genre,
        val movieList: List<Movie>,
    ) : GenreUiState

    data object Failure : GenreUiState
}