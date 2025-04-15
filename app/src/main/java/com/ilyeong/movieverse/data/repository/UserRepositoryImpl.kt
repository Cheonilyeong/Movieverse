package com.ilyeong.movieverse.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ilyeong.movieverse.data.model.WatchlistPostRequest
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.UserApiService
import com.ilyeong.movieverse.data.paging.WatchlistPagingSource
import com.ilyeong.movieverse.domain.model.AccountStates
import com.ilyeong.movieverse.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {
    override fun getWatchlist(page: Int) = flow<List<Movie>> {
        val watchlist = apiService.getWatchlist(page).resultList.map { it.toDomain() }
        emit(watchlist)
    }

    override fun getWatchlistPaging(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { WatchlistPagingSource(apiService) }
        ).flow
    }

    override fun getMovieAccountStates(movieId: Int) = flow<AccountStates> {
        val movieAccountStates = apiService.getMovieAccountStates(movieId).toDomain()
        emit(movieAccountStates)
    }

    override fun addMovieToWatchlist(movieId: Int, watchlist: Boolean) = flow<Unit> {
        val result = apiService.addMovieToWatchlist(
            WatchlistPostRequest(
                mediaType = "movie",
                mediaId = movieId,
                watchlist = watchlist
            )
        )

        when (result.success) {
            true -> emit(Unit)
            false -> throw Exception("Error: ${result.statusCode} - ${result.statusMessage}")
        }
    }
}