package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionIdRequest(
    @SerialName("request_token") val requestToken: String,
)