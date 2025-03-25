package com.ilyeong.movieverse.presentation.genre

import androidx.lifecycle.ViewModel
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.presentation.genre.model.GenreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<GenreUiState>(GenreUiState.Loading)
    val uiState: StateFlow<GenreUiState> = _uiState.asStateFlow()

}