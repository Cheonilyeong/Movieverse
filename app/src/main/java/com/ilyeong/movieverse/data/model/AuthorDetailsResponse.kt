package com.ilyeong.movieverse.data.model

import com.ilyeong.movieverse.domain.model.AuthorDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorDetailsResponse(
    @SerialName("avatar_path") val avatarPath: String = "",
    @SerialName("name") val name: String,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("username") val username: String
)

fun AuthorDetailsResponse.toDomain() = AuthorDetails(
    avatarPath = "https://image.tmdb.org/t/p/original/$avatarPath",
    name = name,
    rating = rating,
    username = username
)