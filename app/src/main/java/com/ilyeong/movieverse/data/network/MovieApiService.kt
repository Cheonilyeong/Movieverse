package com.ilyeong.movieverse.data.network

import com.ilyeong.movieverse.data.model.GenreListResponse
import com.ilyeong.movieverse.data.model.NowPlayingResponse
import com.ilyeong.movieverse.data.model.PopularResponse
import com.ilyeong.movieverse.data.model.TopRatedResponse
import com.ilyeong.movieverse.data.model.TrendingResponse
import com.ilyeong.movieverse.data.model.UpComingResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieList(): TopRatedResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovieList(): UpComingResponse

    @GET("movie/popular")
    suspend fun getPopularMovieList(): PopularResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovieList(): NowPlayingResponse

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovieList(@Path("time_window") timeWindow: String): TrendingResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(): GenreListResponse
}