package com.ilyeong.movieverse.data.model

import com.ilyeong.movieverse.domain.model.SpokenLanguage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguageResponse(
    @SerialName("english_name") val englishName: String,
    @SerialName("iso_639_1") val iso_639_1: String,
    @SerialName("name") val name: String
)

fun SpokenLanguageResponse.toDomain() = SpokenLanguage(
    englishName = englishName,
    iso_639_1 = iso_639_1,
    name = name
)