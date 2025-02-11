package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.TimeWindow

interface MovieRepository {

    suspend fun getTopRatedMovieList(): List<Movie>
    suspend fun getUpcomingMovieList(): List<Movie>
    suspend fun getPopularMovieList(): List<Movie>
    suspend fun getNowPlayingMovieList(): List<Movie>
    suspend fun getTrendingMovieList(timeWindow: TimeWindow): List<Movie>
}