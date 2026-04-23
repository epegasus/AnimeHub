package com.sohaib.animehub.feature.anime.details.state

import com.sohaib.animehub.domain.models.AnimeDetail

data class AnimeDetailsState(
    val isLoading: Boolean = false,
    val animeDetail: AnimeDetail? = null,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
)