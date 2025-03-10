package com.ilyeong.movieverse.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.presentation.watchlist.model.WatchlistUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WatchlistUiState>(WatchlistUiState.Loading)
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    init {
        userRepository.getWatchlistMovieList()
            .onStart {
                // todo
            }
            .catch {
                // todo
            }
            .onEach { watchlist ->
                _uiState.update {
                    WatchlistUiState.Success(watchlist)
                }
            }
            .launchIn(viewModelScope)
    }
}