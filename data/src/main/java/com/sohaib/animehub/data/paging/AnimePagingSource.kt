package com.sohaib.animehub.data.paging

import androidx.paging.PagingSource
import com.sohaib.animehub.core.database.entities.AnimeEntity
import com.sohaib.animehub.data.dataSources.local.AnimeLocalDataSource

/**
 * Offline-first paging source: reads anime rows from Room.
 * Network sync is handled by [AnimeRemoteMediator].
 */
class AnimePagingSource(
    private val localDataSource: AnimeLocalDataSource,
) {
    fun create(): PagingSource<Int, AnimeEntity> = localDataSource.getAnimePagingSource()
}