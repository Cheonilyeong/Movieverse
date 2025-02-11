package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.domain.model.Movie

interface MovieRepository {

    suspend fun getTopRatedMovieList(): List<Movie>
    suspend fun getUpcomingMovieList(): List<Movie>
    suspend fun getPopularMovieList(): List<Movie>
    suspend fun getNowPlayingMovieList(): List<Movie>
}