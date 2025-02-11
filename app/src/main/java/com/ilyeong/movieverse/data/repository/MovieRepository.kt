package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.TimeWindow
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTopRatedMovieList(): Flow<List<Movie>>
    fun getUpcomingMovieList(): Flow<List<Movie>>
    fun getPopularMovieList(): Flow<List<Movie>>
    fun getNowPlayingMovieList(): Flow<List<Movie>>
    fun getTrendingMovieList(timeWindow: TimeWindow): Flow<List<Movie>>
    fun getMovieGenreList(): Flow<List<Genre>>
}