package com.sohaib.animehub.data.repositories

import com.sohaib.animehub.data.dataSources.remote.AnimeRemoteDataSource
import com.sohaib.animehub.data.mapper.toDomain
import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.domain.repositories.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AnimeRepositoryImpl(
    private val remoteDataSource: AnimeRemoteDataSource,
    private val ioDispatchers: CoroutineDispatcher,
) : AnimeRepository {

    override suspend fun getAnimeList(): List<Anime> = withContext(ioDispatchers) {
        remoteDataSource.getAnimeList().toDomain()
    }
}