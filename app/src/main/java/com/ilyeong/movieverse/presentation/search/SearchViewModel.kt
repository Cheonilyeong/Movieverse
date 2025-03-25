package com.ilyeong.movieverse.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.domain.model.TimeWindow
import com.ilyeong.movieverse.presentation.search.model.SearchUiState
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
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Loading)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        movieRepository.getTrendingMovieList(TimeWindow.DAY)
            .onEach { movieList ->
                _uiState.value = SearchUiState.Success(
                    trendingDayMovieList = movieList,
                    searchMovieList = null
                )
            }
            .onStart {
                // todo
            }
            .catch {
                // todo
            }
            .launchIn(viewModelScope)
    }

    fun searchMovie(query: String) {
        if (query.isBlank()) {
            _uiState.update {
                when (it) {
                    SearchUiState.Loading -> {
                        SearchUiState.Loading
                    }

                    is SearchUiState.Success -> {
                        it.copy(searchMovieList = null)
                    }

                    SearchUiState.Failure -> {
                        SearchUiState.Failure
                    }
                }
            }

            return
        }

        movieRepository.searchMovie(query)
            .onEach { movieList ->
                _uiState.update {
                    when (it) {
                        is SearchUiState.Success -> {
                            it.copy(searchMovieList = movieList)
                        }

                        else -> {
                            SearchUiState.Success(
                                trendingDayMovieList = emptyList(),
                                searchMovieList = movieList
                            )
                        }
                    }
                }
            }
            .onStart {
                // todo
            }
            .catch {
                // todo
            }
            .launchIn(viewModelScope)
    }
}