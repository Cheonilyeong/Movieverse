package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyResponse(
    @SerialName("id") val id: Int,
    @SerialName("logo_path") val logoPath: String = "",
    @SerialName("name") val name: String,
    @SerialName("origin_country") val originCountry: String
)