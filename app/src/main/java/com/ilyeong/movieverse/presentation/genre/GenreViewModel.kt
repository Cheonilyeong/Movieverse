package com.ilyeong.movieverse.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.presentation.genre.model.GenreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<GenreUiState>(GenreUiState.Loading)
    val uiState: StateFlow<GenreUiState> = _uiState.asStateFlow()

    fun loadData(genreId: Int) {
        val movieList = movieRepository.getMovieListByGenre(genreId)
        val genreList = movieRepository.getMovieGenreList()

        combine(movieList, genreList) { movieList, genreList ->
            _uiState.update {
                GenreUiState.Success(
                    genre = genreList.first { it.id == genreId },
                    movieList = movieList
                )
            }
        }.onStart {
            when (_uiState.value) {
                is GenreUiState.Loading -> {
                    delay(1000L)
                }      // Shimmer Test
                is GenreUiState.Success -> { /* no-op */
                }

                is GenreUiState.Failure -> TODO()
            }
        }.catch {
            // todo
        }.launchIn(viewModelScope)
    }
}