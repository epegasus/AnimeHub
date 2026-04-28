package com.sohaib.animehub.domain.useCases

import com.sohaib.animehub.domain.repositories.AnimeRepository

class RefreshAnimeListUseCase(private val repository: AnimeRepository) {

    suspend operator fun invoke() = repository.refreshAnimeList()

}