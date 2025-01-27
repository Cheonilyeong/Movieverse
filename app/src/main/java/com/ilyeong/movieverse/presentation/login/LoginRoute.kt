package com.ilyeong.movieverse.presentation.login

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val requestToken: String? = null,
    val approved: Boolean? = null
)