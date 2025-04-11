package com.ilyeong.movieverse.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.MovieApiService
import com.ilyeong.movieverse.data.paging.GenreMoviePagingSource
import com.ilyeong.movieverse.data.paging.SearchPagingSource
import com.ilyeong.movieverse.domain.model.Credit
import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.domain.model.Review
import com.ilyeong.movieverse.domain.model.TimeWindow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {
    override fun getMovieDetail(movieId: Int) = flow<Movie> {
        val movieDetail = apiService.getMovieDetail(movieId).toDomain()
        when (movieDetail.collection) {
            null -> emit(movieDetail)
            else -> {
                val collection = apiService.getMovieCollection(movieDetail.collection.id).toDomain()
                emit(movieDetail.copy(collection = collection))
            }
        }
    }

    override fun getMovieCredit(movieId: Int) = flow<Credit> {
        val movieCredit = apiService.getMovieCredit(movieId).toDomain()
        emit(movieCredit)
    }

    override fun getMovieRecommendationList(movieId: Int) = flow<List<Movie>> {
        val recommendationList =
            apiService.getMovieRecommendationList(movieId).recommendationList.filter { it.mediaType == "movie" }
                .map { it.toDomain() }
        emit(recommendationList)
    }

    override fun getMovieSimilarList(movieId: Int) = flow<List<Movie>> {
        val similarList =
            apiService.getMovieSimilarList(movieId).similarList.map { it.toDomain() }
        emit(similarList)
    }

    override fun getMovieReviewList(movieId: Int) = flow<List<Review>> {
        val reviewList = apiService.getMovieReviewList(movieId).reviewList.map { it.toDomain() }
        emit(reviewList)
    }

    override fun getMovieListByGenrePaging(genreId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GenreMoviePagingSource(apiService, genreId) }
        ).flow
    }

    override fun searchMoviePaging(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingSource(apiService, query) }
        ).flow
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