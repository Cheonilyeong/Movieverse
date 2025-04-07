package com.ilyeong.movieverse.presentation.watchlist.model

sealed interface WatchlistEvent {
    data class ShowMessage(val error: Throwable) : WatchlistEvent
}