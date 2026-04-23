package com.sohaib.animehub.feature.home.effect

sealed class HomeEffect {
    data class NavigateToDetailPage(val animeId: String) : HomeEffect()
}