package com.ilyeong.movieverse.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
//    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadData(movieId: Int) {
        val movieDetail = movieRepository.getMovieDetail(movieId = movieId)
        val movieCredit = movieRepository.getMovieCredit(movieId = movieId)
        val movieRecommendationList = movieRepository.getMovieRecommendationList(movieId = movieId)
        val movieSimilarList = movieRepository.getMovieSimilarList(movieId = movieId)

        combine(
            movieDetail,
            movieCredit,
            movieRecommendationList,
            movieSimilarList
        ) { movie, credit, movieRecommendationList, movieSimilarList ->
            _uiState.update {
                DetailUiState.Success(
                    movie = movie,
                    cast = credit.cast,
                    movieRecommendationList = movieRecommendationList,
                    movieSimilarList = movieSimilarList,
                )
            }
        }.onStart {
            // todo
        }.catch {
            Log.d("DetailViewModel", "error: $it")
            // todo
        }.launchIn(viewModelScope)
    }
}