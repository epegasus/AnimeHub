package com.sohaib.animehub.domain.useCases

import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.domain.repositories.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetAnimeListUseCase(private val repository: AnimeRepository) {

    suspend operator fun invoke(): Flow<List<Anime>> = repository.getAnimeList()

}