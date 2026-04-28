package com.sohaib.animehub.data.repositories

import android.util.Log
import com.sohaib.animehub.core.common.constants.Constants.TAG_DATABASE
import com.sohaib.animehub.data.dataSources.local.AnimeLocalDataSource
import com.sohaib.animehub.data.dataSources.remote.AnimeRemoteDataSource
import com.sohaib.animehub.data.mapper.toDomain
import com.sohaib.animehub.data.mapper.toEntity
import com.sohaib.animehub.domain.errors.DomainError
import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.domain.models.AnimeDetail
import com.sohaib.animehub.domain.repositories.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

class AnimeRepositoryImpl(
    private val localDataSource: AnimeLocalDataSource,
    private val remoteDataSource: AnimeRemoteDataSource,
    private val ioDispatchers: CoroutineDispatcher,
) : AnimeRepository {

    override fun getAnimeList(): Flow<List<Anime>> = localDataSource.getAnimeList().map { it.toDomain() }

    override fun getAnimeDetails(animeId: String): Flow<AnimeDetail?> = localDataSource.getAnimeDetails(animeId = animeId).map { it.toDomain() }

    override suspend fun refreshAnimeList(): Unit = withContext(ioDispatchers) {
        try {
            val response = remoteDataSource.getAnimeList()
            localDataSource.syncList(response.toEntity())
            Log.d(TAG_DATABASE, "AnimeRepositoryImpl: refreshAnimeList: syncList: ${response.data.size} items")
        } catch (throwable: Throwable) {
            throw throwable.toDomainError()
        }
    }

    override suspend fun refreshAnimeDetailById(animeId: String): Unit = withContext(ioDispatchers) {
        try {
            val detail = remoteDataSource.getAnimeDetails(animeId = animeId)
            localDataSource.upsertDetail(detail.toEntity())
            Log.d(TAG_DATABASE, "AnimeRepositoryImpl: refreshAnimeDetailById: Upsert completed")
        } catch (throwable: Throwable) {
            throw throwable.toDomainError()
        }
    }

    private fun Throwable.toDomainError(): DomainError = when (this) {
        is DomainError -> this
        is IOException -> DomainError.Network(this)
        else -> {
            val message = message.orEmpty()
            when {
                message.contains("HTTP 5") -> DomainError.Server(code = 500, original = this)
                message.contains("HTTP 4") -> DomainError.Client(code = 400, original = this)
                else -> DomainError.Unknown(this)
            }
        }
    }
}