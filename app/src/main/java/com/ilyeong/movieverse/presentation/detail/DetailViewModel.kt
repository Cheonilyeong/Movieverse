package com.ilyeong.movieverse.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.domain.model.Credit
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.Review
import com.ilyeong.movieverse.presentation.detail.model.DetailEvent
import com.ilyeong.movieverse.presentation.detail.model.DetailEvent.ShowMessage
import com.ilyeong.movieverse.presentation.detail.model.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadData(movieId: Int) {
        val detailFlow = movieRepository.getMovieDetail(movieId)
        val creditFlow = movieRepository.getMovieCredit(movieId)
        val recommendationListFlow = movieRepository.getMovieRecommendationList(movieId)
        val similarListFlow = movieRepository.getMovieSimilarList(movieId)
        val reviewListFlow = movieRepository.getMovieReviewList(movieId)
        val watchlistFlow = userRepository.getWatchlistMovieList()

        combine(
            detailFlow,
            creditFlow,
            recommendationListFlow,
            similarListFlow,
            reviewListFlow,
            watchlistFlow
        ) {
            val detail = it[0] as Movie
            val credit = it[1] as Credit
            val recommendationList = it[2] as List<Movie>
            val similarList = it[3] as List<Movie>
            val reviewList = it[4] as List<Review>
            val watchlist = it[5] as List<Movie>

            val isInWatchlist = watchlist.any { movie -> movie.id == detail.id }

            _uiState.value = DetailUiState.Success(
                movie = detail.copy(isInWatchlist = isInWatchlist),
                cast = credit.cast,
                collectionMovieList = detail.collection?.partList ?: emptyList(),
                movieRecommendationList = recommendationList,
                movieSimilarList = similarList,
                movieReviewList = reviewList
            )
        }.onStart {
            when (_uiState.value) {
                is DetailUiState.Loading -> delay(1000L)    // Shimmer Test
                is DetailUiState.Success -> {}      // no-op
                is DetailUiState.Failure -> _uiState.value = DetailUiState.Loading
            }
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
            .onStart {
                //  no-op
            }.catch {
                _events.emit(ShowMessage(it))
            }
            .launchIn(viewModelScope)
    }
}