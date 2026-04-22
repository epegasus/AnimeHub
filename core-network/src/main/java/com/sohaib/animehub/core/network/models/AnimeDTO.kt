package com.sohaib.animehub.core.network.models

import com.sohaib.animehub.core.network.models.animeChild.Data
import com.sohaib.animehub.core.network.models.animeChild.LinksXXXXXXXXXXXXXXXXX
import com.sohaib.animehub.core.network.models.animeChild.MetaXX

data class AnimeDTO(
    val `data`: List<Data>,
    val links: LinksXXXXXXXXXXXXXXXXX,
    val meta: MetaXX
)