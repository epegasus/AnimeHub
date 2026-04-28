package com.sohaib.animehub.domain.repositories

import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.domain.models.AnimeDetail
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getAnimeList(): Flow<List<Anime>>
    suspend fun getAnimeDetails(animeId: String): Flow<AnimeDetail?>

    suspend fun refreshAnimeList()
    suspend fun refreshAnimeDetailById(animeId: String)
}