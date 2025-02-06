package com.ilyeong.movieverse.presentation.home.model

import com.ilyeong.movieverse.domain.model.Movie

data class MovieSection(
    val title: String,
    val movieList: List<Movie>,
)