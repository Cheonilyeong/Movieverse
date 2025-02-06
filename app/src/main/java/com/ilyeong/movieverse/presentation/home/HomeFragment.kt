package com.ilyeong.movieverse.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.MarginPageTransformer
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentHomeBinding
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.home.model.MovieSection

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpBanner.offscreenPageLimit = 1
        binding.vpBanner.adapter = MovieBannerAdapter(
            listOf(
                Movie(title = "Movie 1"),
                Movie(title = "Movie 2"),
                Movie(title = "Movie 3"),
                Movie(title = "Movie 4")
            )
        )

        binding.vpBanner.setPageTransformer(
            MarginPageTransformer(resources.getDimensionPixelSize(R.dimen.movieverse_padding_small))
        )

        binding.rvMovieSectionList.adapter = MovieSectionListAdapter(
            listOf(
                MovieSection(
                    title = "Section 1",
                    movieList = listOf(
                        Movie(title = "Movie 1"),
                        Movie(title = "Movie 2"),
                        Movie(title = "Movie 3"),
                        Movie(title = "Movie 4"),
                        Movie(title = "Movie 5"),
                        Movie(title = "Movie 6"),
                        Movie(title = "Movie 7"),
                        Movie(title = "Movie 8"),
                    )
                ),

                MovieSection(
                    title = "Section 2",
                    movieList = listOf(
                        Movie(title = "Movie 1"),
                        Movie(title = "Movie 2"),
                        Movie(title = "Movie 3"),
                        Movie(title = "Movie 4"),
                        Movie(title = "Movie 5"),
                        Movie(title = "Movie 6"),
                        Movie(title = "Movie 7"),
                        Movie(title = "Movie 8"),
                    )
                ),

                MovieSection(
                    title = "Section 3",
                    movieList = listOf(
                        Movie(title = "Movie 1"),
                        Movie(title = "Movie 2"),
                        Movie(title = "Movie 3"),
                        Movie(title = "Movie 4"),
                        Movie(title = "Movie 5"),
                        Movie(title = "Movie 6"),
                        Movie(title = "Movie 7"),
                        Movie(title = "Movie 8"),
                    )
                )
            )
        )
    }
}