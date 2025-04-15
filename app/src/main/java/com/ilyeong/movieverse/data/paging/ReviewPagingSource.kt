package com.ilyeong.movieverse.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.MovieApiService
import com.ilyeong.movieverse.domain.model.Review
import kotlinx.coroutines.delay

class ReviewPagingSource(
    private val apiService: MovieApiService,
    private val movieId: Int
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        delay(1000L)
        val page = params.key ?: 1

        return try {
            val response = apiService.getMovieReviewList(movieId, page)
            val prevPage = if (page == 1) null else page - 1
            val nextPage =
                if (response.reviewList.isEmpty() || page == response.totalPages) null else page + 1

            LoadResult.Page(
                data = response.reviewList.map { it.toDomain() },
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}