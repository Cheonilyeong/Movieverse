package com.ilyeong.movieverse.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.MovieApiService
import com.ilyeong.movieverse.domain.model.Movie

class GenreMoviePagingSource(
    private val movieApiService: MovieApiService,
    private val genreId: Int,
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
            val response = movieApiService.getMovieListByGenre(genreId, page)
            val nextPage =
                if (response.discoverMovieList.isEmpty() || response.page == response.totalPages) null else page + 1

            LoadResult.Page(
                data = response.discoverMovieList.map { it.toDomain() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}