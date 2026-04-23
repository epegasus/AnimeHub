package com.sohaib.animehub.core.network.models.animeChild

data class Attributes(
    val canonicalTitle: String? = null,
    val posterImage: PosterImage? = null,
    val coverImage: CoverImage? = null,
    val titles: Titles? = null,
    val slug: String? = null,
    val description: String? = null,
    val episodeCount: Int? = 0,
    val youtubeVideoId: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)