package com.ilyeong.movieverse.data.network

import com.ilyeong.movieverse.data.model.WatchlistPostRequest
import com.ilyeong.movieverse.data.model.WatchlistPostResponse
import com.ilyeong.movieverse.data.model.WatchlistResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {

    @GET("watchlist/movies")
    suspend fun getWatchlistMovieList(): WatchlistResponse

    @POST("watchlist")
    suspend fun addMovieToWatchlist(@Body request: WatchlistPostRequest): WatchlistPostResponse
}