package com.ilyeong.movieverse.presentation.home.model

sealed interface HomeEvent {
    data class ShowMessage(val error: Throwable) : HomeEvent
}