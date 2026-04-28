package com.sohaib.animehub.feature.anime.details.intent

sealed class AnimeDetailsIntent {
    data class GetData(val animeId: String) : AnimeDetailsIntent()
    object RefreshData : AnimeDetailsIntent()
    object OnNavigateBackClick : AnimeDetailsIntent()
}