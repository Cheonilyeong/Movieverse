package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.UserApiService
import com.ilyeong.movieverse.domain.model.Movie
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {

    override suspend fun getWatchlistMovieList(): List<Movie> {
        return apiService.getWatchlistMovieList().results.map { it.toDomain() }
    }
}