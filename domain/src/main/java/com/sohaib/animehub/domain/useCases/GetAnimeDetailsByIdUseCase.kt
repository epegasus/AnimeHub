package com.sohaib.animehub.domain.useCases

import com.sohaib.animehub.domain.models.AnimeDetail
import com.sohaib.animehub.domain.repositories.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetAnimeDetailsByIdUseCase(private val repository: AnimeRepository) {

    operator fun invoke(animeId: String): Flow<AnimeDetail?> = repository.getAnimeDetails(animeId = animeId)

}