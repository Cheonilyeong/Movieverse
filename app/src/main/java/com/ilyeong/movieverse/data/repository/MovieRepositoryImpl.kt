package com.ilyeong.movieverse.data.repository

import android.util.Log
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.MovieApiService
import com.ilyeong.movieverse.domain.model.Credit
import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.TimeWindow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {
    override fun getMovieDetail(movieId: Int) = flow<Movie> {
        val movieDetail = apiService.getMovieDetail(movieId).toDomain()
        Log.d("MovieRepositoryImpl", "movieDetail: $movieDetail")
        emit(movieDetail)
    }

    override fun getMovieCredit(movieId: Int) = flow<Credit> {
        val movieCredit = apiService.getMovieCredit(movieId).toDomain()
        emit(movieCredit)
    }

    override fun getTopRatedMovieList() = flow<List<Movie>> {
        val topRatedMovieList = apiService.getTopRatedMovieList().resultList.map { it.toDomain() }
        emit(topRatedMovieList)
    }

    override fun getUpcomingMovieList() = flow<List<Movie>> {
        val upComingMovieList = apiService.getUpcomingMovieList().resultList.map { it.toDomain() }
        emit(upComingMovieList)
    }

    override fun getPopularMovieList() = flow<List<Movie>> {
        val popularMovieList = apiService.getPopularMovieList().resultList.map { it.toDomain() }
        emit(popularMovieList)
    }

    override fun getNowPlayingMovieList() = flow<List<Movie>> {
        val nowPlayingMovieList =
            apiService.getNowPlayingMovieList().resultList.map { it.toDomain() }
        emit(nowPlayingMovieList)
    }

    override fun getTrendingMovieList(timeWindow: TimeWindow) = flow<List<Movie>> {
        val trendingMovieList =
            apiService.getTrendingMovieList(timeWindow = timeWindow.name.lowercase()).resultList.map { it.toDomain() }
        emit(trendingMovieList)
    }

    override fun getMovieGenreList() = flow<List<Genre>> {
        val genreList = apiService.getMovieGenreList().genreList.map { it.toDomain() }
        emit(genreList)
    }
}