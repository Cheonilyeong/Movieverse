package com.ilyeong.movieverse.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.TimeWindow
import com.ilyeong.movieverse.presentation.search.model.SearchUiState
import com.ilyeong.movieverse.presentation.search.model.SearchUiState.Loading
import com.ilyeong.movieverse.presentation.search.model.SearchUiState.Trending
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchMoviePaging = _query
        .flatMapLatest {
            when (it.isBlank()) {
                true -> flowOf(PagingData.empty<Movie>())
                false -> movieRepository.searchMoviePaging(it)
            }
        }
        .cachedIn(viewModelScope)

    init {
        movieRepository.getTrendingMovieList(TimeWindow.DAY)
            .onStart { _uiState.value = Loading }
            .onEach { _uiState.value = Trending(it) }
            .catch { _uiState.value = SearchUiState.EmptyPrompt }
            .launchIn(viewModelScope)
    }


    fun setQuery(query: String) {
        _query.value = query
    }
}