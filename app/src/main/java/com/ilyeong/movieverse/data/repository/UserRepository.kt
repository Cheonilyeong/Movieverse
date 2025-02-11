package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.domain.model.Movie

interface UserRepository {

    suspend fun getWatchlistMovieList(): List<Movie>
}