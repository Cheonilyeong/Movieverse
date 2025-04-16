package com.ilyeong.movieverse.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ilyeong.movieverse.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    val watchlistPaging = userRepository.getWatchlistMoviePaging().cachedIn(viewModelScope)
}