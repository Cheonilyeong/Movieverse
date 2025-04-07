package com.ilyeong.movieverse.presentation.search.model

sealed interface SearchEvent {
    data class ShowMessage(val error: Throwable) : SearchEvent
}