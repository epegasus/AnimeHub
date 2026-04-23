package com.sohaib.animehub.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.feature.home.state.HomeState
import com.sohaib.animehub.feature.home.viewModel.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import com.sohaib.animehub.core.common.R as commonR

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect {

        }
    }

    HomeScreenContent(
        modifier = modifier,
        state = state.value
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeState,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.isError) {
            HomeScreen_ErrorState()
        } else if (state.isEmpty) {
            HomeScreen_EmptyState()
        } else if (state.isLoading) {
            HomeScreen_LoadingState()
        } else {
            HomeScreen_SuccessState(state.animeList)
        }
    }
}

@Composable
private fun HomeScreen_SuccessState(list: List<Anime>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list, key = { it.id }) { anime ->
            AnimeItem(
                anime = anime
            )
        }
    }
}

@Composable
private fun HomeScreen_LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeScreen_EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(commonR.string.no_data_found))
    }
}

@Composable
private fun HomeScreen_ErrorState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(commonR.string.error_found))
    }
}

@Composable
fun AnimeItem(
    modifier: Modifier = Modifier,
    anime: Anime,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    HomeScreenContent(
        state = HomeState(
            animeList = listOf(
                Anime(
                    id = "1",
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
        )
    )
}