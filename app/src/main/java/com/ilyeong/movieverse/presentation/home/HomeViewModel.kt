package com.ilyeong.movieverse.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.domain.model.TimeWindow
import com.ilyeong.movieverse.presentation.home.model.HomeContent.BannerContent
import com.ilyeong.movieverse.presentation.home.model.HomeContent.GenreContent
import com.ilyeong.movieverse.presentation.home.model.HomeContent.SectionContent
import com.ilyeong.movieverse.presentation.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieRepository: MovieRepository,
    userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        val trendingDayFlow =
            movieRepository.getTrendingMovieList(TimeWindow.DAY).map { BannerContent(it.take(5)) }
        val genreFlow =
            movieRepository.getMovieGenreList().map { GenreContent(it) }
        val topRatedFlow =
            movieRepository.getTopRatedMovieList().map { SectionContent("Top Rated", it) }
        val upcomingFlow =
            movieRepository.getUpcomingMovieList().map { SectionContent("Upcoming", it) }
        val popularFlow =
            movieRepository.getPopularMovieList().map { SectionContent("Popular", it) }
        val nowPlayingFlow =
            movieRepository.getNowPlayingMovieList().map { SectionContent("Now Playing", it) }
        val trendingWeekFlow = movieRepository.getTrendingMovieList(TimeWindow.WEEK)
            .map { SectionContent("Trending Week", it) }
        val watchlistFlow =
            userRepository.getWatchlistMovieList().map { SectionContent("Watchlist", it) }

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
            _uiState.update {
                HomeUiState.Success(dataList.toList())
            }
        }.onStart {
            // todo
        }.catch {
            // todo
            Log.d("Home", "catch: $it")
        }.launchIn(viewModelScope)
    }
}