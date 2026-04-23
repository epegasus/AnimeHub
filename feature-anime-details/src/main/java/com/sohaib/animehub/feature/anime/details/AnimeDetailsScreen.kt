package com.sohaib.animehub.feature.anime.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.sohaib.animehub.domain.models.AnimeDetail
import com.sohaib.animehub.feature.anime.details.effect.AnimeDetailsEffect
import com.sohaib.animehub.feature.anime.details.intent.AnimeDetailsIntent
import com.sohaib.animehub.feature.anime.details.state.AnimeDetailsState
import com.sohaib.animehub.feature.anime.details.viewModel.AnimeDetailsViewModel
import org.koin.androidx.compose.koinViewModel

const val ANIME_DETAILS_ROUTE = "anime_details/{animeId}"
fun createRouteAnimeDetails(animeId: String) = "anime_details/$animeId"

@Composable
fun AnimeDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: AnimeDetailsViewModel = koinViewModel(),
    animeId: String? = null,
    onNavigateBack: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(animeId) {
        viewModel.handleIntent(AnimeDetailsIntent.FetchData(animeId.orEmpty()))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                AnimeDetailsEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    AnimeDetailsContent(
        modifier = modifier,
        state = state.value,
        onNavigateBack = { viewModel.handleIntent(AnimeDetailsIntent.OnNavigateBackClick) },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AnimeDetailsContent(
    modifier: Modifier = Modifier,
    state: AnimeDetailsState,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Anime Details") },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) {
                        Text(text = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.isError -> Text(text = "Error Found")
                state.isEmpty -> Text(text = "No Data Found")
                state.animeDetail != null -> AnimeDetailsSuccessState(animeDetail = state.animeDetail)
            }
        }
    }
}

@Composable
private fun AnimeDetailsSuccessState(
    animeDetail: AnimeDetail,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AsyncImage(
            model = animeDetail.coverImageLargeUrl.ifBlank { animeDetail.posterImageLargeUrl },
            contentDescription = animeDetail.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
        )
        Text(text = animeDetail.title, style = MaterialTheme.typography.headlineSmall)
        Text(text = animeDetail.description, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Episodes: ${animeDetail.episodeCount}", style = MaterialTheme.typography.bodyMedium)
    }
}