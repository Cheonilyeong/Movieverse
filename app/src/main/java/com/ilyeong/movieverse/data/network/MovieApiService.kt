package com.ilyeong.movieverse.data.network

import com.ilyeong.movieverse.data.model.CollectionResponse
import com.ilyeong.movieverse.data.model.CreditResponse
import com.ilyeong.movieverse.data.model.DiscoverResponse
import com.ilyeong.movieverse.data.model.GenreListResponse
import com.ilyeong.movieverse.data.model.MovieDetailResponse
import com.ilyeong.movieverse.data.model.NowPlayingResponse
import com.ilyeong.movieverse.data.model.PopularResponse
import com.ilyeong.movieverse.data.model.RecommendationListResponse
import com.ilyeong.movieverse.data.model.ReviewListResponse
import com.ilyeong.movieverse.data.model.SearchMovieListResponse
import com.ilyeong.movieverse.data.model.SimilarListResponse
import com.ilyeong.movieverse.data.model.TopRatedResponse
import com.ilyeong.movieverse.data.model.TrendingResponse
import com.ilyeong.movieverse.data.model.UpComingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredit(@Path("movie_id") movieId: Int): CreditResponse

    @GET("collection/{collection_id}")
    suspend fun getMovieCollection(@Path("collection_id") collectionId: Int): CollectionResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendationList(@Path("movie_id") movieId: Int): RecommendationListResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getMovieSimilarList(@Path("movie_id") movieId: Int): SimilarListResponse

    @GET("discover/movie")
    suspend fun getMovieListByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): DiscoverResponse

    @GET("search/movie")
    suspend fun searchMovieList(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): SearchMovieListResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviewList(@Path("movie_id") movieId: Int): ReviewListResponse

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