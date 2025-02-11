package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getWatchlistMovieList(): Flow<List<Movie>>
}