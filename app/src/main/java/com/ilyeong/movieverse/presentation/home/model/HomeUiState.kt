package com.ilyeong.movieverse.presentation.home.model

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Success(
        val homeContentItemList: List<HomeContent>,
    ) : HomeUiState

    data object Failure : HomeUiState
}

