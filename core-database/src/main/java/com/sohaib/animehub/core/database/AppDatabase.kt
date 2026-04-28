package com.sohaib.animehub.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sohaib.animehub.core.database.daos.AnimeDao
import com.sohaib.animehub.core.database.entities.AnimeEntity

@Database(
    entities = [AnimeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

}