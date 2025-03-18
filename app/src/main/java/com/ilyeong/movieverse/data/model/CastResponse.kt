package com.ilyeong.movieverse.data.model

import com.ilyeong.movieverse.domain.model.Cast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    @SerialName("adult") val adult: Boolean,
    @SerialName("cast_id") val castId: Int,
    @SerialName("character") val character: String,
    @SerialName("credit_id") val creditId: String,
    @SerialName("gender") val gender: Int,
    @SerialName("id") val id: Int,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("name") val name: String,
    @SerialName("order") val order: Int,
    @SerialName("original_name") val originalName: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("profile_path") val profilePath: String = ""
)

fun CastResponse.toDomain() = Cast(
    adult = adult,
    castId = castId,
    character = character,
    creditId = creditId,
    gender = gender,
    id = id,
    knownForDepartment = knownForDepartment,
    name = name,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = "https://image.tmdb.org/t/p/original/$profilePath",
)