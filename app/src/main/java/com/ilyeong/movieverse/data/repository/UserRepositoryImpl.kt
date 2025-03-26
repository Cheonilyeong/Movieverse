package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.data.model.WatchlistPostRequest
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.UserApiService
import com.ilyeong.movieverse.domain.model.Movie
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {

    override fun getWatchlistMovieList() = flow<List<Movie>> {
        val watchlistMovieList = apiService.getWatchlistMovieList().resultList.map { it.toDomain() }
        emit(watchlistMovieList)
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