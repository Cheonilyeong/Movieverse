package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.MovieApiService
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.TimeWindow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    override fun getTopRatedMovieList() = flow<List<Movie>> {
        val topRatedMovieList = apiService.getTopRatedMovieList().results.map { it.toDomain() }
        emit(topRatedMovieList)
    }

    override fun getUpcomingMovieList() = flow<List<Movie>> {
        val upComingMovieList = apiService.getUpcomingMovieList().results.map { it.toDomain() }
        emit(upComingMovieList)
    }

    override fun getPopularMovieList() = flow<List<Movie>> {
        val popularMovieList = apiService.getPopularMovieList().results.map { it.toDomain() }
        emit(popularMovieList)
    }

    override fun getNowPlayingMovieList() = flow<List<Movie>> {
        val nowPlayingMovieList = apiService.getNowPlayingMovieList().results.map { it.toDomain() }
        emit(nowPlayingMovieList)
    }

    override fun getTrendingMovieList(timeWindow: TimeWindow) = flow<List<Movie>> {
        val trendingMovieList =
            apiService.getTrendingMovieList(timeWindow = timeWindow.name.lowercase()).results.map { it.toDomain() }
        emit(trendingMovieList)
    }
}