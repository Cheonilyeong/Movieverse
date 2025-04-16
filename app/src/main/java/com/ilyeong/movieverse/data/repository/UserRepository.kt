package com.ilyeong.movieverse.data.repository

import androidx.paging.PagingData
import com.ilyeong.movieverse.domain.model.Account
import com.ilyeong.movieverse.domain.model.AccountStates
import com.ilyeong.movieverse.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAccount(): Flow<Account>
    fun getWatchlist(page: Int): Flow<List<Movie>>
    fun getWatchlistPaging(): Flow<PagingData<Movie>>
    fun getMovieAccountStates(movieId: Int): Flow<AccountStates>
    fun addMovieToWatchlist(movieId: Int, watchlist: Boolean): Flow<Unit>
}