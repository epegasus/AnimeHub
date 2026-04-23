package com.sohaib.animehub.domain.repositories

import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.domain.models.AnimeDetail

interface AnimeRepository {
    suspend fun getAnimeList(): List<Anime>
    suspend fun getAnimeDetails(animeId: String): AnimeDetail
}