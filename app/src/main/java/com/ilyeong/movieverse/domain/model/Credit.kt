package com.ilyeong.movieverse.domain.model

data class Credit(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)