package com.ilyeong.movieverse.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import com.ilyeong.movieverse.data.repository.UserRepository
import com.ilyeong.movieverse.domain.model.TimeWindow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            val topRatedMovieList = movieRepository.getTopRatedMovieList()
            val upcomingMovieList = movieRepository.getUpcomingMovieList()
            val popularMovieList = movieRepository.getPopularMovieList()
            val nowPlayingMovieList = movieRepository.getNowPlayingMovieList()
            val trendingDayMovieList =
                movieRepository.getTrendingMovieList(timeWindow = TimeWindow.DAY)
            val trendingWeekMovieList =
                movieRepository.getTrendingMovieList(timeWindow = TimeWindow.WEEK)
            val watchlistMovieList = userRepository.getWatchlistMovieList()


            Log.d("HomeViewModel", "TopRated: $topRatedMovieList")
            Log.d("HomeViewModel", "Upcoming: $upcomingMovieList")
            Log.d("HomeViewModel", "Popular: $popularMovieList")
            Log.d("HomeViewModel", "NowPlaying: $nowPlayingMovieList")
            Log.d("HomeViewModel", "Trending: $trendingDayMovieList")
            Log.d("HomeViewModel", "Trending: $trendingWeekMovieList")
            Log.d("HomeViewModel", "Watchlist: $watchlistMovieList")
        }
    }
}