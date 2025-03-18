package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationListResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val recommendationList: List<RecommendationResponse>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

fun RecommendationListResponse.toDomain() = recommendationList.map { it.toDomain() }