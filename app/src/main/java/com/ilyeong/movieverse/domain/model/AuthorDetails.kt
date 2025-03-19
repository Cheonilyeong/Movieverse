package com.ilyeong.movieverse.domain.model

data class AuthorDetails(
    val avatarPath: String,
    val name: String,
    val rating: Double,
    val username: String
)