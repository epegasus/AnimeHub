package com.sohaib.animehub.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings
import androidx.room.Upsert
import com.sohaib.animehub.core.database.entities.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    /* -------------------------------- Reading Data -------------------------------- */

    @SuppressWarnings(RoomWarnings.QUERY_MISMATCH)
    @Query("SELECT id, title, posterImageLargeUrl FROM table_anime")
    fun getAllAnimes(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM table_anime WHERE id = :animeId")
    fun getAnimeById(animeId: String): Flow<AnimeEntity?>

    @Query("SELECT * FROM table_anime WHERE id IN (:animeIds)")
    suspend fun getAnimeByIds(animeIds: List<String>): List<AnimeEntity>

    /* -------------------------------- Writing Data -------------------------------- */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnore(list: List<AnimeEntity>): List<Long>

    @Query("""UPDATE table_anime SET title = :title, posterImageLargeUrl = :posterImageLargeUrl WHERE id = :id """)
    suspend fun updateListFields(id: String, title: String, posterImageLargeUrl: String)

    @Upsert
    suspend fun upsertAnime(anime: AnimeEntity)

    @Query("DELETE FROM table_anime WHERE id NOT IN (:ids)")
    suspend fun deleteNotIn(ids: List<String>)
}