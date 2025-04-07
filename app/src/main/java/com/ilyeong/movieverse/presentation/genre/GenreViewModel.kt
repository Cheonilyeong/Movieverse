package com.ilyeong.movieverse.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.presentation.genre.model.GenreEvent
import com.ilyeong.movieverse.presentation.genre.model.GenreEvent.ShowMessage
import com.ilyeong.movieverse.presentation.genre.model.GenreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<GenreUiState>(GenreUiState.Loading)
    val uiState: StateFlow<GenreUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<GenreEvent>()
    val events = _events.asSharedFlow()

    fun loadData(genreId: Int) {
        val movieList = movieRepository.getMovieListByGenre(genreId)
        val genreList = movieRepository.getMovieGenreList()

        combine(movieList, genreList) { movieList, genreList ->
            _uiState.value = GenreUiState.Success(
                genre = genreList.first { it.id == genreId },
                movieList = movieList
            )
        }.onStart {
            when (_uiState.value) {
                is GenreUiState.Loading -> {}       // no-op
                is GenreUiState.Success -> {}       // no-op
                is GenreUiState.Failure -> _uiState.value = GenreUiState.Loading
            }
            delay(1000L)     // Shimmer Test
        }.catch {
            when (_uiState.value) {
                is GenreUiState.Loading -> _uiState.value = GenreUiState.Failure
                is GenreUiState.Success -> _events.emit(ShowMessage(it))
                is GenreUiState.Failure -> _events.emit(ShowMessage(it))
            }
        }.launchIn(viewModelScope)
    }
}