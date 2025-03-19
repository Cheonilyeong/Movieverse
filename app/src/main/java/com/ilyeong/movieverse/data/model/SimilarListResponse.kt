package com.ilyeong.movieverse.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarListResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val similarList: List<SimilarResponse> = emptyList(),
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

fun SimilarListResponse.toDomain() = similarList.map { it.toDomain() }