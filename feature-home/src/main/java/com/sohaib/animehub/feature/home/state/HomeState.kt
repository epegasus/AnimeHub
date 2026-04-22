package com.sohaib.animehub.feature.home.state

import com.sohaib.animehub.domain.models.Anime

data class HomeState(
    val isLoading: Boolean = false,
    val animeList: List<Anime> = emptyList(),
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
)