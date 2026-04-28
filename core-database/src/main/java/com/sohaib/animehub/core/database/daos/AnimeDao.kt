package com.sohaib.animehub.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sohaib.animehub.core.database.entities.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    /* -------------------------------- Reading Data -------------------------------- */

    @Query("SELECT id, title, posterImageLargeUrl FROM table_anime")
    fun getAllAnimes(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM table_anime WHERE id = :animeId")
    fun getAnimeById(animeId: String): Flow<AnimeEntity?>

    /* -------------------------------- Writing Data -------------------------------- */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: List<AnimeEntity>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(animeEntity: AnimeEntity): Int

}