package com.ilyeong.movieverse.data.network

import com.ilyeong.movieverse.data.model.NowPlayingResponse
import com.ilyeong.movieverse.data.model.PopularResponse
import com.ilyeong.movieverse.data.model.UpComingResponse
import com.ilyeong.movieverse.data.model.WatchlistResponse
import retrofit2.http.GET

interface MovieApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieList(): WatchlistResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovieList(): UpComingResponse

    @GET("movie/popular")
    suspend fun getPopularMovieList(): PopularResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovieList(): NowPlayingResponse

}