package com.ilyeong.movieverse.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.presentation.watchlist.model.WatchlistUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WatchlistUiState>(WatchlistUiState.Loading)
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    fun loadData() {
        userRepository.getWatchlistMovieList()
            .onStart {
                when (_uiState.value) {
                    is WatchlistUiState.Loading -> {
                        delay(1000L)
                    }       // Shimmer Test
                    is WatchlistUiState.Success -> {}
                    is WatchlistUiState.Failure -> {}
                }
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