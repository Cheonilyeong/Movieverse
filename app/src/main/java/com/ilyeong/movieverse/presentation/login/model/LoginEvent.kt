package com.ilyeong.movieverse.presentation.login.model

sealed interface LoginEvent {
    data class NavigateToCustomTabs(val url: String) : LoginEvent
    data object NavigateToMain : LoginEvent
    data class ShowMessage(val error: Throwable) : LoginEvent
}