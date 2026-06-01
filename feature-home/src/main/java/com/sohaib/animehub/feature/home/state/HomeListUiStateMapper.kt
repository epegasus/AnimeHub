package com.sohaib.animehub.feature.home.state

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.sohaib.animehub.domain.errors.DomainError
import com.sohaib.animehub.domain.models.Anime
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.sohaib.animehub.core.common.R as commonR

fun LazyPagingItems<Anime>.toHomeListUiState(): HomeListUiState {
    val refreshState = loadState.refresh
    val appendState = loadState.append

    return when (refreshState) {
        is LoadState.Loading if itemCount == 0 -> HomeListUiState.FirstTimeLoading
        is LoadState.Error if itemCount == 0 -> HomeListUiState.Error(refreshState.error.toUiMessageRes())
        is LoadState.NotLoading if itemCount == 0 && appendState.endOfPaginationReached -> HomeListUiState.Empty
        else -> HomeListUiState.Success(
            isRefreshing = refreshState is LoadState.Loading && itemCount > 0,
            isLoadingMore = appendState is LoadState.Loading,
        )
    }
}

private fun Throwable.toUiMessageRes(): Int = when (this) {
    is DomainError.Server -> commonR.string.error_server
    is DomainError.Client -> commonR.string.error_client
    is DomainError.Unknown -> commonR.string.error_unknown
    is DomainError.Network,
    is UnknownHostException,
    is ConnectException,
    is SocketTimeoutException,
    -> commonR.string.error_network
    is IOException -> commonR.string.error_network
    else -> commonR.string.error_unknown
}