package com.sohaib.animehub.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_anime_remote_keys")
data class AnimeRemoteKeysEntity(
    @PrimaryKey
    val id: Int = REMOTE_KEYS_ID,
    val nextOffset: Int?,
) {
    companion object {
        const val REMOTE_KEYS_ID = 0
    }
}