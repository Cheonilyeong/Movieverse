package com.ilyeong.movieverse.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.MovieApiService
import com.ilyeong.movieverse.domain.model.Movie

class SearchPagingSource(
    private val apiService: MovieApiService,
    private val query: String
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            val response = apiService.searchMovieList(query = query, page = page)
            val nextPage =
                if (response.searchMovieList.isEmpty() || response.page == response.totalPages) null else page + 1

            LoadResult.Page(
                data = response.searchMovieList.map { it.toDomain() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}