package com.sohaib.animehub.data.dataSources.local

import com.sohaib.animehub.core.database.daos.AnimeDao
import com.sohaib.animehub.core.database.daos.AnimeRemoteKeysDao
import com.sohaib.animehub.core.database.entities.AnimeEntity
import com.sohaib.animehub.core.database.entities.AnimeRemoteKeysEntity
import kotlinx.coroutines.flow.Flow

class AnimeLocalDataSource(
    private val animeDao: AnimeDao,
    private val remoteKeysDao: AnimeRemoteKeysDao,
) {

    /* ------------------------------------------ AnimeDao ------------------------------------------ */

    suspend fun upsertAnimePage(entities: List<AnimeEntity>) {
        if (entities.isEmpty()) return

        animeDao.insertIgnore(entities)
        entities.forEach { entity ->
            animeDao.updateListFields(
                id = entity.id,
                title = entity.title,
                posterImageLargeUrl = entity.posterImageLargeUrl,
            )
        }
    }

    suspend fun upsertDetail(anime: AnimeEntity) = animeDao.upsertAnime(anime)

    fun getAnimeDetails(animeId: String): Flow<AnimeEntity?> = animeDao.getAnimeById(animeId = animeId)

    fun getAnimePagingSource() = animeDao.getAnimePagingSource()

    suspend fun getAnimeCount(): Int = animeDao.getAnimeCount()

    /* ------------------------------------------ RemoteKeysDao ------------------------------------------ */

    suspend fun getNextOffset(): Int? = remoteKeysDao.getRemoteKeys()?.nextOffset

    suspend fun saveNextOffset(nextOffset: Int?) = remoteKeysDao.insertRemoteKeys(AnimeRemoteKeysEntity(nextOffset = nextOffset))

    suspend fun clearPagingCache() {
        animeDao.clearAll()
        remoteKeysDao.clearAll()
    }
}