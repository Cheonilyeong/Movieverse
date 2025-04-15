package com.ilyeong.movieverse.data.network

import com.ilyeong.movieverse.BuildConfig
import com.ilyeong.movieverse.data.model.AccountStatesResponse
import com.ilyeong.movieverse.data.model.WatchlistPostRequest
import com.ilyeong.movieverse.data.model.WatchlistPostResponse
import com.ilyeong.movieverse.data.model.WatchlistResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {

    @GET("account/${BuildConfig.ACCOUNT_ID}/watchlist/movies")
    suspend fun getWatchlist(@Query("page") page: Int): WatchlistResponse

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieAccountStates(@Path("movie_id") movieId: Int): AccountStatesResponse

    @POST("account/${BuildConfig.ACCOUNT_ID}/watchlist")
    suspend fun addMovieToWatchlist(@Body request: WatchlistPostRequest): WatchlistPostResponse
}