package com.sohaib.animehub.domain.repositories

import com.sohaib.animehub.domain.models.Anime

interface AnimeRepository {
    suspend fun getAnimeList(): List<Anime>
}