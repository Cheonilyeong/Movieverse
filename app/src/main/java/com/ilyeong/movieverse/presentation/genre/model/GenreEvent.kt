package com.ilyeong.movieverse.presentation.genre.model

sealed interface GenreEvent {
    data class ShowMessage(val error: Throwable) : GenreEvent
}