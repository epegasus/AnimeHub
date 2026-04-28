package com.sohaib.animehub.data.dataSources.local

import com.sohaib.animehub.core.database.daos.AnimeDao
import com.sohaib.animehub.core.database.entities.AnimeEntity
import kotlinx.coroutines.flow.Flow

class AnimeLocalDataSource(private val dao: AnimeDao) {

    fun getAnimeList(): Flow<List<AnimeEntity>> = dao.getAllAnimes()

    fun getAnimeDetails(animeId: String): Flow<AnimeEntity?> = dao.getAnimeById(animeId = animeId)

    suspend fun insertAll(list: List<AnimeEntity>): List<Long> = dao.insertAll(animeList = list)

    suspend fun updateItem(animeEntity: AnimeEntity): Int = dao.updateItem(animeEntity = animeEntity)

}