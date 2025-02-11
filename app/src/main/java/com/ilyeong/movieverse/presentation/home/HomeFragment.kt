package com.ilyeong.movieverse.presentation.home

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import com.ilyeong.movieverse.R
import com.ilyeong.movieverse.databinding.FragmentHomeBinding
import com.ilyeong.movieverse.domain.model.Movie
import com.ilyeong.movieverse.presentation.common.BaseFragment
import com.ilyeong.movieverse.presentation.home.model.MovieSection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HomeFragment", "$viewModel")

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

        binding.rvMovieGenre.adapter = MovieGenreAdapter(
            listOf(
                "공포",
                "코미디",
                "애니메이션",
                "액션",
                "판타지",
                "가족",
                "SF",
                "다큐멘터리"
            )
        )

        binding.rvMovieGenre.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val itemCount = state.itemCount
                val context = parent.context

                if (position == 0) {
                    // 첫 번째 아이템
                    outRect.left =
                        context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                } else if (position == itemCount - 1) {
                    // 마지막 아이템
                    outRect.right =
                        context.resources.getDimensionPixelOffset(R.dimen.movieverse_padding_medium)
                }
            }
        })

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