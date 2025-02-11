package com.ilyeong.movieverse.data.network

import com.ilyeong.movieverse.data.model.WatchlistResponse
import retrofit2.http.GET

interface MovieApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieList(): WatchlistResponse
}