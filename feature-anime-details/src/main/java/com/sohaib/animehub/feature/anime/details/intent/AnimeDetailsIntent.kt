package com.sohaib.animehub.feature.anime.details.intent

sealed class AnimeDetailsIntent {
    data class FetchData(val animeId: String) : AnimeDetailsIntent()
    object OnNavigateBackClick : AnimeDetailsIntent()
}