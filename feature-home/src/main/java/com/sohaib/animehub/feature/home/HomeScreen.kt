package com.sohaib.animehub.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.sohaib.animehub.domain.errors.DomainError
import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.feature.home.effect.HomeEffect
import com.sohaib.animehub.feature.home.intent.HomeIntent
import com.sohaib.animehub.feature.home.viewModel.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.sohaib.animehub.core.common.R as commonR

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToDetailPage: (String) -> Unit,
) {
    val pagingItems = viewModel.animePagingFlow.collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToDetailPage -> onNavigateToDetailPage(effect.animeId)
            }
        }
    }

    HomeScreenContent(
        modifier = modifier,
        pagingItems = pagingItems,
        onCardClick = { viewModel.handleIntent(HomeIntent.OnItemClick(it)) },
        onRetryClicked = { pagingItems.refresh() },
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<Anime>,
    onCardClick: (String) -> Unit,
    onRetryClicked: () -> Unit,
) {
    val refreshState = pagingItems.loadState.refresh
    val isInitialLoading = refreshState is LoadState.Loading && pagingItems.itemCount == 0
    val initialError = refreshState as? LoadState.Error
    val isInitialError = initialError != null && pagingItems.itemCount == 0
    val isEmpty = refreshState is LoadState.NotLoading &&
            pagingItems.itemCount == 0 &&
            pagingItems.loadState.append.endOfPaginationReached

    Box(modifier = modifier.fillMaxSize()) {
        when {
            isInitialLoading -> HomeScreenLoadingState()
            isInitialError -> HomeScreenErrorState(
                messageResId = initialError.error.toUiMessageRes(),
                onRetryClicked = onRetryClicked,
            )

            isEmpty -> HomeScreenEmptyState()
            else -> HomeScreenSuccessState(pagingItems = pagingItems, onCardClick = onCardClick)
        }

        AnimatedVisibility(
            visible = refreshState is LoadState.Loading && pagingItems.itemCount > 0,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier.align(Alignment.TopCenter),
        ) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        if (pagingItems.loadState.append is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            )
        }
    }
}

@Composable
private fun HomeScreenLoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeScreenErrorState(
    messageResId: Int,
    onRetryClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = stringResource(messageResId), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetryClicked) {
            Text(text = stringResource(commonR.string.retry))
        }
    }
}

@Composable
private fun HomeScreenEmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = stringResource(commonR.string.no_data_found), textAlign = TextAlign.Center)
    }
}

@Composable
private fun HomeScreenSuccessState(
    pagingItems: LazyPagingItems<Anime>,
    onCardClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id },
        ) { index ->
            val anime = pagingItems[index] ?: return@items
            AnimeItem(anime = anime, onCardClick = onCardClick)
        }
    }
}

@Composable
fun AnimeItem(
    modifier: Modifier = Modifier,
    anime: Anime,
    onCardClick: (String) -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        onClick = { onCardClick(anime.id) },
    ) {
        Box {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
            Text(
                text = anime.title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                    .padding(8.dp)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

private fun Throwable.toUiMessageRes(): Int = when (this) {
    is DomainError.Server -> commonR.string.error_server
    is DomainError.Client -> commonR.string.error_client
    is DomainError.Unknown -> commonR.string.error_unknown
    is DomainError.Network, is UnknownHostException, is ConnectException, is SocketTimeoutException -> commonR.string.error_network
    is IOException -> commonR.string.error_network
    else -> commonR.string.error_unknown
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPrev() {
    AnimeItem(
        anime = Anime(id = "1", title = "Naruto", imageUrl = ""),
        onCardClick = {},
    )
}