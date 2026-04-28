package com.sohaib.animehub.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_anime")
data class AnimeEntity(
    @PrimaryKey
    val id: String,

    // list fields
    val title: String,
    val posterImageLargeUrl: String,

    // detail fields (nullable initially)
    val coverImageLargeUrl: String? = null,
    val description: String? = null,
    val slug: String? = null,
    val episodeCount: Int? = null,
    val youtubeVideoId: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)