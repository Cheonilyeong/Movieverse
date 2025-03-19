package com.ilyeong.movieverse.data.model

import com.ilyeong.movieverse.domain.model.Credit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditResponse(
    @SerialName("cast") val cast: List<CastResponse> = emptyList(),
    @SerialName("crew") val crew: List<CrewResponse> = emptyList(),
    @SerialName("id") val id: Int
)

fun CreditResponse.toDomain() = Credit(
    cast = cast.map { it.toDomain() },
    crew = crew.map { it.toDomain() },
    id = id
)