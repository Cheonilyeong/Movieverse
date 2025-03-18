package com.ilyeong.movieverse.data.model

import com.ilyeong.movieverse.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreListResponse(
    @SerialName("genres") val genreList: List<GenreResponse> = emptyList()
)

@Serializable
data class GenreResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)

fun GenreResponse.toDomain() = Genre(id = id, name = name)