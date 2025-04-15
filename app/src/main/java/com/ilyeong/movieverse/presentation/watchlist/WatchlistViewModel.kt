package com.ilyeong.movieverse.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.presentation.watchlist.model.WatchlistEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    val watchlistPaging = userRepository.getWatchlistPaging()
        .cachedIn(viewModelScope)

    private val _events = MutableSharedFlow<WatchlistEvent>()
    val events = _events.asSharedFlow()
}