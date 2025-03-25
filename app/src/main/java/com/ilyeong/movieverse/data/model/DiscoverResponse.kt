package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscoverResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val discoverMovieList: List<MovieResponse> = emptyList(),
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
