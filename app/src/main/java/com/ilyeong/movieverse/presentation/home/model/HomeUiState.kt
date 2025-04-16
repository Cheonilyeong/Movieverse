package com.ilyeong.movieverse.presentation.home.model

import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.domain.model.Movie

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val bannerMovieList: List<Movie>,
        val genreList: List<Genre>,
    ) : HomeUiState

    data object Failure : HomeUiState
}

