package com.sohaib.animehub.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sohaib.animehub.core.database.daos.AnimeDao
import com.sohaib.animehub.core.database.daos.AnimeRemoteKeysDao
import com.sohaib.animehub.core.database.entities.AnimeEntity
import com.sohaib.animehub.core.database.entities.AnimeRemoteKeysEntity

@Database(
    entities = [
        AnimeEntity::class,
        AnimeRemoteKeysEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    abstract fun animeRemoteKeysDao(): AnimeRemoteKeysDao

}