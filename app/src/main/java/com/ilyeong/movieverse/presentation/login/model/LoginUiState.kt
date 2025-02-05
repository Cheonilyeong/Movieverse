package com.ilyeong.movieverse.presentation.login.model

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)