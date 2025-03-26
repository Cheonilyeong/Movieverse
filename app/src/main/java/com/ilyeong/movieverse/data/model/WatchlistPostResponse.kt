package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchlistPostResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String,
)