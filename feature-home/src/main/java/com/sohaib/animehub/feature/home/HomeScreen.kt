package com.sohaib.animehub.feature.home

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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.feature.home.effect.HomeEffect
import com.sohaib.animehub.feature.home.intent.HomeIntent
import com.sohaib.animehub.feature.home.state.HomeState
import com.sohaib.animehub.feature.home.viewModel.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import com.sohaib.animehub.core.common.R as commonR

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToDetailPage: (String) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToDetailPage -> onNavigateToDetailPage(effect.animeId)
            }
        }
    }

    HomeScreenContent(
        modifier = modifier,
        state = state.value,
        onCardClick = { viewModel.handleIntent(HomeIntent.OnItemClick(it)) },
        onRetryClicked = { viewModel.handleIntent(HomeIntent.RefreshData) }
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onCardClick: (String) -> Unit,
    onRetryClicked: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> HomeScreenLoadingState()
            state.isError -> HomeScreenErrorState(onRetryClicked)
            state.isEmpty -> HomeScreenEmptyState()
            else -> HomeScreenSuccessState(list = state.animeList, onCardClick = onCardClick)
        }
    }
}

@Composable
private fun HomeScreenLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeScreenErrorState(
    onRetryClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(commonR.string.error_found))
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onRetryClicked
        ) {
            Text(text = stringResource(commonR.string.retry))
        }
    }
}

@Composable
private fun HomeScreenEmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(commonR.string.no_data_found))
    }
}

@Composable
private fun HomeScreenSuccessState(
    list: List<Anime>,
    onCardClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list, key = { it.id }) { anime ->
            AnimeItem(
                anime = anime,
                onCardClick = onCardClick
            )
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
        onClick = { onCardClick(anime.id) }
    ) {
        Box {
            AsyncImage(
                model = anime.smallImageUrl,
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
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
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPrev() {
    HomeScreenContent(
        state = HomeState(
            animeList = listOf(
                Anime(
                    id = "0",
                    title = "Naruto",
                    smallImageUrl = "",
                ),
                Anime(
                    id = "1",
                    title = "Naruto",
                    smallImageUrl = "",
                ),
                Anime(
                    id = "2",
                    title = "Naruto",
                    smallImageUrl = "",
                ),
                Anime(
                    id = "3",
                    title = "Naruto",
                    smallImageUrl = "",
                ),
            )
        ),
        onCardClick = {},
        onRetryClicked = {}
    )
}