package com.ilyeong.movieverse.presentation.home.model

import com.ilyeong.movieverse.domain.model.Genre
import com.ilyeong.movieverse.domain.model.Movie

sealed interface HomeContent {

    fun getViewType(): Int

    data class BannerContent(
        val movieList: List<Movie>,
    ) : HomeContent {
        override fun getViewType() = BANNER_CONTENT
    }

    data class GenreContent(
        val genreList: List<Genre>,
    ) : HomeContent {
        override fun getViewType() = GENRE_CONTENT
    }

    data class SectionContent(
        val title: String,
        val movieList: List<Movie>,
    ) : HomeContent {
        override fun getViewType() = SECTION_CONTENT
    }

    companion object {
        const val BANNER_CONTENT = 1
        const val GENRE_CONTENT = 2
        const val SECTION_CONTENT = 3
    }
}