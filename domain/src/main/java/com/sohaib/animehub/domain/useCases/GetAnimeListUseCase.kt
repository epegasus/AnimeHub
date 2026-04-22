package com.sohaib.animehub.domain.useCases

import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.domain.repositories.AnimeRepository

class GetAnimeListUseCase(private val repository: AnimeRepository) {

    suspend operator fun invoke(): List<Anime> = repository.getAnimeList()

}