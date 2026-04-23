package com.sohaib.animehub.feature.home.intent

sealed class HomeIntent {
    object FetchData : HomeIntent()
    data class OnItemClick(val animeId: String) : HomeIntent()
}