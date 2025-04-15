package com.ilyeong.movieverse.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.TimeWindow
import com.ilyeong.movieverse.presentation.home.model.HomeEvent
import com.ilyeong.movieverse.presentation.home.model.HomeEvent.ShowMessage
import com.ilyeong.movieverse.presentation.home.model.HomeUiState
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
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<HomeEvent>()
    val events = _events.asSharedFlow()

    fun loadData() {
        val trendingDayFlow = movieRepository.getTrendingMovieList(TimeWindow.DAY)
        val genreFlow = movieRepository.getMovieGenreList()
        val topRatedFlow = movieRepository.getTopRatedMovieList()
        val upcomingFlow = movieRepository.getUpcomingMovieList()
        val popularFlow = movieRepository.getPopularMovieList()
        val nowPlayingFlow = movieRepository.getNowPlayingMovieList()
        val trendingWeekFlow = movieRepository.getTrendingMovieList(TimeWindow.WEEK)
        val watchlistFlow = userRepository.getWatchlist(1)

        combine(
            trendingDayFlow,
            genreFlow,
            topRatedFlow,
            upcomingFlow,
            popularFlow,
            nowPlayingFlow,
            trendingWeekFlow,
            watchlistFlow
        ) { dataList ->
            val bannerMovieList = dataList[0] as List<Movie>
            val genreList = dataList[1] as List<Genre>
            val topRatedMovieList = dataList[2] as List<Movie>
            val upcomingMovieList = dataList[3] as List<Movie>
            val popularMovieList = dataList[4] as List<Movie>
            val nowPlayingMovieList = dataList[5] as List<Movie>
            val trendingWeekMovieList = dataList[6] as List<Movie>
            val watchlistMovieList = dataList[7] as List<Movie>

            _uiState.value = HomeUiState.Success(
                bannerMovieList = bannerMovieList.take(5),
                genreList = genreList,
                topRatedMovieList = topRatedMovieList,
                upcomingMovieList = upcomingMovieList,
                popularMovieList = popularMovieList,
                nowPlayingMovieList = nowPlayingMovieList,
                trendingWeekMovieList = trendingWeekMovieList,
                watchlistMovieList = watchlistMovieList,
            )
        }.onStart {
            when (_uiState.value) {
                is HomeUiState.Loading -> {}    // no-op
                is HomeUiState.Success -> {}    // no-op
                is HomeUiState.Failure -> _uiState.value = HomeUiState.Loading
            }
            delay(1000L)    // Shimmer Tess
        }.catch { error ->
            when (_uiState.value) {
                is HomeUiState.Loading -> _uiState.value = HomeUiState.Failure
                is HomeUiState.Success -> _events.emit(ShowMessage(error))
                is HomeUiState.Failure -> _events.emit(ShowMessage(error))
            }
        }.launchIn(viewModelScope)
    }
}