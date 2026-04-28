package com.sohaib.animehub.domain.models

data class AnimeDetail(
    val id: String,
    val title: String,
    val coverImageLargeUrl: String,
    val posterImageLargeUrl: String,
    val description: String,
    val slug: String,
    val episodeCount: Int,
    val youtubeVideoId: String,
    val createdAt: String,
    val updatedAt: String,
)