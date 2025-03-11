package com.ilyeong.movieverse.presentation.home.model

import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.domain.model.Movie

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val bannerMovieList: List<Movie>,
        val genreList: List<Genre>,
        val topRatedMovieList: List<Movie>,
        val upcomingMovieList: List<Movie>,
        val popularMovieList: List<Movie>,
        val nowPlayingMovieList: List<Movie>,
        val trendingWeekMovieList: List<Movie>,
        val watchlistMovieList: List<Movie>,
    ) : HomeUiState

    data object Failure : HomeUiState
}

