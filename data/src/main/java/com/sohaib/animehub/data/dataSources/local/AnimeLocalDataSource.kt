package com.sohaib.animehub.data.dataSources.local

import androidx.room.withTransaction
import com.sohaib.animehub.core.database.AppDatabase
import com.sohaib.animehub.core.database.entities.AnimeEntity
import com.sohaib.animehub.core.database.entities.AnimeRemoteKeysEntity
import kotlinx.coroutines.flow.Flow

class AnimeLocalDataSource(
    private val database: AppDatabase,
) {

    private val animeDao get() = database.animeDao()
    private val remoteKeysDao get() = database.animeRemoteKeysDao()

    /* ------------------------------------------ AnimeDao ------------------------------------------ */

    fun getAnimeDetails(animeId: String): Flow<AnimeEntity?> = animeDao.getAnimeById(animeId = animeId)

    fun getAnimePagingSource() = animeDao.getAnimePagingSource()

    suspend fun getAnimeCount(): Int = animeDao.getAnimeCount()

    suspend fun upsertDetail(anime: AnimeEntity) = animeDao.upsertAnime(anime)

    /**
     * Applies one remote page atomically:
     * - optional cache clear on refresh
     * - list upsert
     * - remote key update
     *
     * Network I/O must happen before calling this.
     */
    suspend fun applyPagedRemoteResponse(
        isRefresh: Boolean,
        entities: List<AnimeEntity>,
        nextOffset: Int?,
    ) {
        database.withTransaction {
            if (isRefresh) {
                animeDao.clearAll()
                remoteKeysDao.clearAll()
            }

            if (entities.isNotEmpty()) {
                animeDao.insertIgnore(entities)
                entities.forEach { entity ->
                    animeDao.updateListFields(
                        id = entity.id,
                        title = entity.title,
                        posterImageLargeUrl = entity.posterImageLargeUrl,
                    )
                }
            }

            remoteKeysDao.insertRemoteKeys(
                AnimeRemoteKeysEntity(nextOffset = nextOffset),
            )
        }
    }

    /* ------------------------------------------ RemoteKeysDao ------------------------------------------ */

    suspend fun getNextOffset(): Int? = remoteKeysDao.getRemoteKeys()?.nextOffset
}
