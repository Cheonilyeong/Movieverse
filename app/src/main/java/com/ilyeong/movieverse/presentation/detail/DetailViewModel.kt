package com.ilyeong.movieverse.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadData(movieId: Int) {
        val movieDetail = movieRepository.getMovieDetail(movieId)

        movieDetail.flatMapLatest { movie ->
            val movieCredit = movieRepository.getMovieCredit(movieId)
            val movieCollection = movie.collection?.let {
                movieRepository.getMovieCollection(movie.collection.id).map { it.partList }
            } ?: flow { emit(emptyList<Movie>()) }
            val movieRecommendationList = movieRepository.getMovieRecommendationList(movieId)
            val movieSimilarList = movieRepository.getMovieSimilarList(movieId)
            val movieReviewList = movieRepository.getMovieReviewList(movieId)

            combine(
                movieCredit,
                movieCollection,
                movieRecommendationList,
                movieSimilarList,
                movieReviewList
            ) { credit, collection, recommendationList, similarList, reviewList ->
                _uiState.update {
                    DetailUiState.Success(
                        movie = movie,
                        cast = credit.cast,
                        collectionMovieList = collection,
                        movieRecommendationList = recommendationList,
                        movieSimilarList = similarList,
                        movieReviewList = reviewList
                    )
                }
            }
        }.onStart {
            // todo
        }.catch {
            Log.d("DetailViewModel", "error: $it")
            // todo
        }.launchIn(viewModelScope)
    }
}