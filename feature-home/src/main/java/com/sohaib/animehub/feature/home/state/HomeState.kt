package com.sohaib.animehub.feature.home.state

import com.sohaib.animehub.domain.models.Anime

data class HomeState(
    val uiState: HomeUiState = HomeUiState.Loading,
    val isRefreshing: Boolean = false,
    val hasCompletedInitialSync: Boolean = false,
)

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Empty : HomeUiState
    data class Error(val messageResId: Int) : HomeUiState
    data class Success(val animeList: List<Anime>) : HomeUiState
}