package com.ilyeong.movieverse.presentation.profile.model

import androidx.annotation.StringRes

sealed interface ProfileEvent {
    data object NavigateToLogin : ProfileEvent
    data class ShowMessage(@StringRes val message: Int) : ProfileEvent
}