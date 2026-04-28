package com.sohaib.animehub.core.network.models

import com.sohaib.animehub.core.network.models.animeChild.Data
import com.sohaib.animehub.core.network.models.animeChild.LinksPage
import com.sohaib.animehub.core.network.models.animeChild.MetaCount

data class AnimeDTO(
    val `data`: List<Data> = emptyList(),
    val links: LinksPage? = null,
    val meta: MetaCount? = null,
)