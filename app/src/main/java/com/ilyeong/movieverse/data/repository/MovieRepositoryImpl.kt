package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.MovieApiService
import com.ilyeong.movieverse.domain.model.Movie
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    override suspend fun getTopRatedMovieList(): List<Movie> {
        return apiService.getTopRatedMovieList().results.map { it.toDomain() }
    }

    override suspend fun getUpcomingMovieList(): List<Movie> {
        return apiService.getUpcomingMovieList().results.map { it.toDomain() }
    }

    override suspend fun getPopularMovieList(): List<Movie> {
        return apiService.getPopularMovieList().results.map { it.toDomain() }
    }
}