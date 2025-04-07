package com.ilyeong.movieverse.presentation.detail.model

sealed interface DetailEvent {
    data class ShowMessage(val error: Throwable) : DetailEvent
}