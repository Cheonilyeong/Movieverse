package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryResponse(
    @SerialName("iso_3166_1") val iso_3166_1: String,
    @SerialName("name") val name: String
)