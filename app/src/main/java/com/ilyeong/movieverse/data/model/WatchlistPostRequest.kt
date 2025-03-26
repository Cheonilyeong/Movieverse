package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchlistPostRequest(
    @SerialName("media_type") val mediaType: String,
    @SerialName("media_id") val mediaId: Int,
    @SerialName("watchlist") val watchlist: Boolean
)