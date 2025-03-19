package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingResponse(
    @SerialName("dates") val date: Date,
    @SerialName("page") val page: Int,
    @SerialName("results") val resultList: List<MovieResponse> = emptyList(),
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)