package com.sohaib.animehub.data.dataSources.local

import com.sohaib.animehub.core.database.daos.AnimeDao
import com.sohaib.animehub.core.database.entities.AnimeEntity
import kotlinx.coroutines.flow.Flow

class AnimeLocalDataSource(private val dao: AnimeDao) {

    fun getAnimeList(): Flow<List<AnimeEntity>> = dao.getAllAnimes()

    fun getAnimeDetails(animeId: String): Flow<AnimeEntity?> = dao.getAnimeById(animeId = animeId)

    suspend fun syncList(list: List<AnimeEntity>) {
        if (list.isEmpty()) return

        // 1️⃣ insert new items
        dao.insertIgnore(list)

        // 2️⃣ update only list fields (safe)
        list.forEach {
            dao.updateListFields(
                id = it.id,
                title = it.title,
                posterImageLargeUrl = it.posterImageLargeUrl
            )
        }

        // 3️⃣ optional cleanup
        dao.deleteNotIn(list.map { it.id })
    }

    suspend fun upsertDetail(anime: AnimeEntity) = dao.upsertAnime(anime)
}