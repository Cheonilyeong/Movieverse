package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GravatarResponse(
    @SerialName("hash") val hash: String = ""
)