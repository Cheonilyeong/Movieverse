package com.ilyeong.movieverse.data.network

import com.ilyeong.movieverse.data.model.WatchlistResponse
import retrofit2.http.GET

interface UserApiService {

    @GET("watchlist/movies")
    suspend fun getWatchlistMovieList(): WatchlistResponse
}