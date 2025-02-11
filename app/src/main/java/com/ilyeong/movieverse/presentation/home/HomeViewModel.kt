package com.ilyeong.movieverse.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.domain.model.TimeWindow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    init {
        // 배너
        val trendingDayFlow =
            movieRepository.getTrendingMovieList(TimeWindow.DAY).map { it.take(5) }

        // 장르
        val genreFlow = movieRepository.getMovieGenreList()

        // 섹션
        val topRatedFlow = movieRepository.getTopRatedMovieList()
        val upcomingFlow = movieRepository.getUpcomingMovieList()
        val popularFlow = movieRepository.getPopularMovieList()
        val nowPlayingFlow = movieRepository.getNowPlayingMovieList()

        val trendingWeekFlow = movieRepository.getTrendingMovieList(TimeWindow.WEEK)
        val watchlistFlow = userRepository.getWatchlistMovieList()

        combine(
            topRatedFlow,
            genreFlow,
            upcomingFlow,
            popularFlow,
            nowPlayingFlow,
            trendingDayFlow,
            trendingWeekFlow,
            watchlistFlow
        ) { dataList ->
            val topRated = dataList[0]
            val genre = dataList[1]
            val upcoming = dataList[2]
            val popular = dataList[3]
            val nowPlaying = dataList[4]
            val trendingDay = dataList[5]
            val trendingWeek = dataList[6]
            val watchlist = dataList[7]

            Log.d("HomeViewModel", "dataList: $dataList")

        }.onStart {
            // todo
        }.catch {
            // todo
        }.launchIn(viewModelScope)
    }
}