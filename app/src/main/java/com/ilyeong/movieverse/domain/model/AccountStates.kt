package com.ilyeong.movieverse.domain.model

data class AccountStates(
    val id: Int,
    val favorite: Boolean,
    val rated: Int?,
    val watchlist: Boolean
)
