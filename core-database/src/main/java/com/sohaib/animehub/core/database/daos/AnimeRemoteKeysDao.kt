package com.sohaib.animehub.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sohaib.animehub.core.database.entities.AnimeRemoteKeysEntity

@Dao
interface AnimeRemoteKeysDao {

    @Query("SELECT * FROM table_anime_remote_keys WHERE id = :id LIMIT 1")
    suspend fun getRemoteKeys(id: Int = AnimeRemoteKeysEntity.REMOTE_KEYS_ID): AnimeRemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(keys: AnimeRemoteKeysEntity)

    @Query("DELETE FROM table_anime_remote_keys")
    suspend fun clearAll()
}