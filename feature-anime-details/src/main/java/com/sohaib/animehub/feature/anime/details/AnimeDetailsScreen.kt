package com.sohaib.animehub.feature.anime.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sohaib.animehub.feature.anime.details.viewModel.AnimeDetailsViewModel
import org.koin.androidx.compose.koinViewModel

const val ANIME_DETAILS_ROUTE = "anime_details/{animeId}"
fun createRouteAnimeDetails(animeId: String) = "anime_details/$animeId"

@Composable
fun AnimeDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: AnimeDetailsViewModel = koinViewModel(),
    animeId: String? = null,
) {

}