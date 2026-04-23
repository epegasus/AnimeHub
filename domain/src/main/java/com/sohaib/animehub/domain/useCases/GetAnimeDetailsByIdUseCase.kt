package com.sohaib.animehub.domain.useCases

import com.sohaib.animehub.domain.models.AnimeDetail
import com.sohaib.animehub.domain.repositories.AnimeRepository

class GetAnimeDetailsByIdUseCase(private val repository: AnimeRepository) {

    suspend operator fun invoke(animeId: String): AnimeDetail = repository.getAnimeDetails(animeId = animeId)

}