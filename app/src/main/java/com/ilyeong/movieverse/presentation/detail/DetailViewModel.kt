package com.ilyeong.movieverse.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.domain.model.AccountStates
import com.ilyeong.movieverse.domain.model.Credit
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.Review
import com.ilyeong.movieverse.presentation.detail.model.DetailEvent
import com.ilyeong.movieverse.presentation.detail.model.DetailEvent.ShowMessage
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<DetailEvent>()
    val events = _events.asSharedFlow()

    fun loadData(movieId: Int) {
        val detailFlow = movieRepository.getMovieDetail(movieId)
        val accountStatesFlow = userRepository.getMovieAccountStates(movieId)
        val creditFlow = movieRepository.getMovieCredit(movieId)
        val recommendationListFlow = movieRepository.getMovieRecommendationList(movieId)
        val similarListFlow = movieRepository.getMovieSimilarList(movieId)
        val reviewListFlow = movieRepository.getMovieReviewList(movieId)

        combine(
            detailFlow,
            accountStatesFlow,
            creditFlow,
            recommendationListFlow,
            similarListFlow,
            reviewListFlow,
        ) {
            val detail = it[0] as Movie
            val accountStates = it[1] as AccountStates
            val credit = it[2] as Credit
            val recommendationList = it[3] as List<Movie>
            val similarList = it[4] as List<Movie>
            val reviewList = it[5] as List<Review>

            _uiState.value = DetailUiState.Success(
                movie = detail.copy(isInWatchlist = accountStates.watchlist),
                cast = credit.cast,
                collectionMovieList = detail.collection?.partList ?: emptyList(),
                movieRecommendationList = recommendationList,
                movieSimilarList = similarList,
                movieReviewList = reviewList
            )
        }.onStart {
            when (_uiState.value) {
                is DetailUiState.Loading -> {}      // no-op
                is DetailUiState.Success -> {}      // no-op
                is DetailUiState.Failure -> _uiState.value = DetailUiState.Loading
            }
            delay(1000L)    // Shimmer Test
        }.catch {
            when (_uiState.value) {
                is DetailUiState.Loading -> _uiState.value = DetailUiState.Failure
                is DetailUiState.Success -> _events.emit(ShowMessage(it))
                is DetailUiState.Failure -> _events.emit(ShowMessage(it))
            }
        }.launchIn(viewModelScope)
    }

    fun addMovieToWatchlist() {
        val currentState = uiState.value as? DetailUiState.Success ?: return

        val movieId = currentState.movie.id
        val watchlist = currentState.movie.isInWatchlist.not()

        userRepository.addMovieToWatchlist(movieId, watchlist)
            .onEach {
                _uiState.value =
                    currentState.copy(movie = currentState.movie.copy(isInWatchlist = watchlist))
            }
            .catch {
                _events.emit(ShowMessage(it))
            }
            .launchIn(viewModelScope)
    }
}