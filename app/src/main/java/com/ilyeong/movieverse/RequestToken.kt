package com.ilyeong.movieverse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestToken(
    @SerialName("expires_at") val expiresAt: String,
    @SerialName("request_token") val requestToken: String,
    @SerialName("success") val success: Boolean
)