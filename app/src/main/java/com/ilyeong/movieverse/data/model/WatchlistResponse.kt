package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchlistResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val resultList: List<MovieResponse>,
    @SerialName("total_pages") val totalPage: Int,
    @SerialName("total_results") val totalResults: Int
)