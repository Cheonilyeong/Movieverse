package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionIdResponse(
    @SerialName("success") val success: Boolean,
    @SerialName("session_id") val sessionId: String,
)