package com.ilyeong.movieverse.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.domain.model.TimeWindow
import com.ilyeong.movieverse.presentation.search.model.SearchEvent
import com.ilyeong.movieverse.presentation.search.model.SearchEvent.ShowMessage
import com.ilyeong.movieverse.presentation.search.model.SearchUiState
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
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SearchEvent>()
    val events = _events.asSharedFlow()

    fun loadData() {
        movieRepository.getTrendingMovieList(TimeWindow.DAY)
            .onEach { movieList ->
                _uiState.value = SearchUiState.Success(
                    trendingDayMovieList = movieList,
                    searchMovieList = null
                )
            }
            .onStart {
                when (_uiState.value) {
                    is SearchUiState.Loading -> {}      // no-op
                    is SearchUiState.Success -> {}      // no-op
                    is SearchUiState.Failure -> _uiState.value = SearchUiState.Loading
                }
                delay(1000L)        // Shimmer Test
            }
            .catch {
                when (_uiState.value) {
                    is SearchUiState.Loading -> _uiState.value = SearchUiState.Failure
                    is SearchUiState.Success -> _events.emit(ShowMessage(it))
                    is SearchUiState.Failure -> _events.emit(ShowMessage(it))
                }
            }
            .launchIn(viewModelScope)
    }

    fun searchMovie(query: String) {
        val currentState = _uiState.value as? SearchUiState.Success ?: return

        if (query.isBlank()) {
            _uiState.value = currentState.copy(searchMovieList = null)
            return
        }

        movieRepository.searchMovie(query)
            .onEach { movieList ->
                _uiState.value = currentState.copy(searchMovieList = movieList)
            }
            .catch {
                when (_uiState.value) {
                    is SearchUiState.Loading -> _uiState.value = SearchUiState.Failure
                    is SearchUiState.Success -> _events.emit(ShowMessage(it))
                    is SearchUiState.Failure -> _events.emit(ShowMessage(it))
                }
            }
            .launchIn(viewModelScope)
    }
}