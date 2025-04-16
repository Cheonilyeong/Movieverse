package com.ilyeong.movieverse.presentation.profile.model

import com.ilyeong.movieverse.domain.model.Account

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val account: Account) : ProfileUiState
    data object Failure : ProfileUiState
}