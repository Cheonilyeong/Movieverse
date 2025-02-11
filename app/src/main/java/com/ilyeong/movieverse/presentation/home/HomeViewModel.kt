package com.ilyeong.movieverse.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyeong.movieverse.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            val topRatedMovieList = movieRepository.getTopRatedMovieList()
            val upcomingMovieList = movieRepository.getUpcomingMovieList()
            val popularMovieList = movieRepository.getPopularMovieList()
            val nowPlayingMovieList = movieRepository.getNowPlayingMovieList()


            Log.d("HomeViewModel", "TopRated: $topRatedMovieList")
            Log.d("HomeViewModel", "Upcoming: $upcomingMovieList")
            Log.d("HomeViewModel", "Popular: $popularMovieList")
            Log.d("HomeViewModel", "NowPlaying: $nowPlayingMovieList")
        }
    }
}