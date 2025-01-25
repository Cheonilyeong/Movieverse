package com.ilyeong.movieverse.data.model

import com.ilyeong.movieverse.domain.model.RequestToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenResponse(
    @SerialName("expires_at") val expiresAt: String,
    @SerialName("request_token") val requestToken: String,
    @SerialName("success") val success: Boolean
)

fun RequestTokenResponse.toDomain(): RequestToken {
    require(success) { "알 수 없는 오류가 발생했습니다." }
    return RequestToken(requestToken = requestToken)
}
