package com.ilyeong.movieverse.presentation.watchlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ilyeong.movieverse.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    val watchlistPaging = userRepository.getWatchlistMoviePaging().cachedIn(viewModelScope)
        .onEach { Log.d("watchlistfragment", "New paging data emitted ${it}") }

    init {
        Log.d("WatchlistFragment", "viewmodel init")
    }
}