package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.domain.model.Movie

interface MovieRepository {
    suspend fun getTopRatedMovieList(): List<Movie>
}