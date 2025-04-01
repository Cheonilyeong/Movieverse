package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.domain.model.Credit
import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.Review
import com.ilyeong.movieverse.domain.model.TimeWindow
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovieDetail(movieId: Int): Flow<Movie>
    fun getMovieCredit(movieId: Int): Flow<Credit>
    fun getMovieRecommendationList(movieId: Int): Flow<List<Movie>>
    fun getMovieSimilarList(movieId: Int): Flow<List<Movie>>
    fun getMovieReviewList(movieId: Int): Flow<List<Review>>
    fun getMovieListByGenre(genreId: Int): Flow<List<Movie>>

    fun searchMovie(query: String): Flow<List<Movie>>

    fun getTopRatedMovieList(): Flow<List<Movie>>
    fun getUpcomingMovieList(): Flow<List<Movie>>
    fun getPopularMovieList(): Flow<List<Movie>>
    fun getNowPlayingMovieList(): Flow<List<Movie>>
    fun getTrendingMovieList(timeWindow: TimeWindow): Flow<List<Movie>>
    fun getMovieGenreList(): Flow<List<Genre>>
}