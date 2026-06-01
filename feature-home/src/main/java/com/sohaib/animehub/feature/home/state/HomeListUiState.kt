package com.sohaib.animehub.feature.home.state

sealed interface HomeListUiState {
    data object FirstTimeLoading : HomeListUiState
    data object Empty : HomeListUiState
    data class Error(val messageResId: Int) : HomeListUiState
    data class Success(val isRefreshing: Boolean = false, val isLoadingMore: Boolean = false) : HomeListUiState
}