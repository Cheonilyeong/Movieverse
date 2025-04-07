package com.ilyeong.movieverse.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.presentation.watchlist.model.WatchlistEvent
import com.ilyeong.movieverse.presentation.watchlist.model.WatchlistEvent.ShowMessage
import com.ilyeong.movieverse.presentation.watchlist.model.WatchlistUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WatchlistUiState>(WatchlistUiState.Loading)
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<WatchlistEvent>()
    val events = _events.asSharedFlow()

    fun loadData() {
        userRepository.getWatchlistMovieList()
            .onStart {
                when (_uiState.value) {
                    is WatchlistUiState.Loading -> {}       // no-op
                    is WatchlistUiState.Success -> {}       // no-op
                    is WatchlistUiState.Failure -> _uiState.value = WatchlistUiState.Loading
                }
                delay(1000L)     // Shimmer Test
            }
            .catch { error ->
                when (_uiState.value) {
                    is WatchlistUiState.Loading -> _uiState.value = WatchlistUiState.Failure
                    is WatchlistUiState.Success -> _events.emit(ShowMessage(error))
                    is WatchlistUiState.Failure -> _events.emit(ShowMessage(error))
                }
            }
            .onEach { watchlist ->
                _uiState.value = WatchlistUiState.Success(watchlist)
            }
            .launchIn(viewModelScope)
    }
}