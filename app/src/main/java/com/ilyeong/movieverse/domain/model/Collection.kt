package com.ilyeong.movieverse.domain.model

data class Collection(
    val backdropPath: String,
    val id: Int,
    val name: String,
    val overview: String,
    val partList: List<Movie>,
    val posterPath: String
)