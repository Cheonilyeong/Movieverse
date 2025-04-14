package com.ilyeong.movieverse.data.model

import com.ilyeong.movieverse.domain.model.AccountStates
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountStatesResponse(
    @SerialName("id") val id: Int = 0,
    @SerialName("favorite") val favorite: Boolean = false,
    @SerialName("rated") val rated: RatedResponse? = null,
    @SerialName("watchlist") val watchlist: Boolean = true
)

fun AccountStatesResponse.toDomain() = AccountStates(
    id = id,
    favorite = favorite,
    rated = rated?.value,
    watchlist = watchlist
)