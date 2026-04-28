package com.sohaib.animehub.domain.useCases

import com.sohaib.animehub.domain.repositories.AnimeRepository

class RefreshAnimeDetailsByIdUseCase(private val repository: AnimeRepository) {

    suspend operator fun invoke(animeId: String) = repository.refreshAnimeDetailById(animeId = animeId)

}