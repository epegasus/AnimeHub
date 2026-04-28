package com.sohaib.animehub.feature.home.intent

sealed class HomeIntent {
    object GetData : HomeIntent()
    object RefreshData : HomeIntent()
    data class OnItemClick(val animeId: String) : HomeIntent()
}