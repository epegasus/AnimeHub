package com.sohaib.animehub.feature.anime.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

const val ANIME_DETAILS_ROUTE = "anime_details/{animeId}"
fun createRouteAnimeDetails(animeId: String) = "anime_details/$animeId"

@Composable
fun AnimeDetailsScreen(
    modifier: Modifier = Modifier,
    animeId: String? = null,

    ) {

}