package com.ilyeong.movieverse.data.model

import com.ilyeong.movieverse.data.RatedSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = RatedSerializer::class)
data class RatedResponse(
    @SerialName("value") val value: Int
)