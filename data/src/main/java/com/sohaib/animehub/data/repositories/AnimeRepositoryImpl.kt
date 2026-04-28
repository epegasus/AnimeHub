package com.sohaib.animehub.data.repositories

import android.util.Log
import com.sohaib.animehub.core.common.constants.Constants.TAG_DATABASE
import com.sohaib.animehub.data.dataSources.local.AnimeLocalDataSource
import com.sohaib.animehub.data.dataSources.remote.AnimeRemoteDataSource
import com.sohaib.animehub.data.mapper.toDomain
import com.sohaib.animehub.data.mapper.toEntity
import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.domain.models.AnimeDetail
import com.sohaib.animehub.domain.repositories.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AnimeRepositoryImpl(
    private val localDataSource: AnimeLocalDataSource,
    private val remoteDataSource: AnimeRemoteDataSource,
    private val ioDispatchers: CoroutineDispatcher,
) : AnimeRepository {

    override suspend fun getAnimeList(): Flow<List<Anime>> {
        refreshAnimeList()

        return localDataSource.getAnimeList().map { it.toDomain() }
    }

    override suspend fun getAnimeDetails(animeId: String): Flow<AnimeDetail?> {
        refreshAnimeDetailById(animeId = animeId)

        return localDataSource.getAnimeDetails(animeId = animeId).map { it.toDomain() }
    }

    override suspend fun refreshAnimeList() = withContext(ioDispatchers) {
        val list = remoteDataSource.getAnimeList()
        val rowIds = localDataSource.insertAll(list.toEntity())
        rowIds.forEach {
            Log.d(TAG_DATABASE, "AnimeRepositoryImpl: refreshAnimeList: Inserted Row Id = $it")
        }
    }

    override suspend fun refreshAnimeDetailById(animeId: String): Unit = withContext(ioDispatchers) {
        val animeDetailDto = remoteDataSource.getAnimeDetails(animeId = animeId)
        val rowId = localDataSource.updateItem(animeDetailDto.toEntity())
        Log.d(TAG_DATABASE, "AnimeRepositoryImpl: refreshAnimeDetailById: Updated Row Id = $rowId")
    }
}