package com.ilyeong.movieverse.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.MovieApiService
import com.ilyeong.movieverse.data.paging.GenreMoviePagingSource
import com.ilyeong.movieverse.data.paging.NowPlayingPagingSource
import com.ilyeong.movieverse.data.paging.PopularPagingSource
import com.ilyeong.movieverse.data.paging.ReviewPagingSource
import com.ilyeong.movieverse.data.paging.SearchPagingSource
import com.ilyeong.movieverse.data.paging.TopRatedPagingSource
import com.ilyeong.movieverse.data.paging.TrendingPagingSource
import com.ilyeong.movieverse.data.paging.UpcomingPagingSource
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

    override fun getMovieReviewPaging(movieId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ReviewPagingSource(apiService, movieId) }
        ).flow
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

    override fun getTopRatedMoviePaging(maxPage: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TopRatedPagingSource(apiService, maxPage) }
        ).flow
    }

    override fun getUpcomingMoviePaging(maxPage: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UpcomingPagingSource(apiService, maxPage) }
        ).flow
    }

    override fun getPopularMoviePaging(maxPage: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PopularPagingSource(apiService, maxPage) }
        ).flow
    }

    override fun getNowPlayingMoviePaging(maxPage: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NowPlayingPagingSource(apiService, maxPage) }
        ).flow
    }

    override fun getTrendingMovieList(timeWindow: TimeWindow) = flow<List<Movie>> {
        val trendingMovieList =
            apiService.getTrendingMovieList(timeWindow.name.lowercase()).resultList.map { it.toDomain() }
        emit(trendingMovieList)
    }

    override fun getTrendingMoviePaging(
        timeWindow: TimeWindow,
        maxPage: Int
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TrendingPagingSource(
                    apiService = apiService,
                    timeWindow = timeWindow.name.lowercase(),
                    maxPage = maxPage
                )
            }
        ).flow
    }

    override fun getMovieGenreList() = flow<List<Genre>> {
        val genreList = apiService.getMovieGenreList().genreList.map { it.toDomain() }
        emit(genreList)
    }
}