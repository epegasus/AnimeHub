package com.sohaib.animehub.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sohaib.animehub.core.common.constants.Constants.TAG_DATABASE
import com.sohaib.animehub.core.database.entities.AnimeEntity
import com.sohaib.animehub.core.network.models.AnimeDTO
import com.sohaib.animehub.data.dataSources.local.AnimeLocalDataSource
import com.sohaib.animehub.data.dataSources.remote.AnimeRemoteDataSource
import com.sohaib.animehub.data.mapper.toEntity

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val localDataSource: AnimeLocalDataSource,
    private val remoteDataSource: AnimeRemoteDataSource,
) : RemoteMediator<Int, AnimeEntity>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(loadType: LoadType, state: PagingState<Int, AnimeEntity>): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    localDataSource.getNextOffset()
                        ?: localDataSource.getAnimeCount().takeIf { it > 0 }
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            // Network first — never hold a DB transaction during remote I/O.
            val response = remoteDataSource.getAnimeList(
                limit = AnimePagingConfig.PAGE_SIZE,
                offset = offset,
            )
            val entities = response.toEntity()
            val endOfPaginationReached = entities.isEmpty() || !response.hasNextPage()
            val nextOffset = if (endOfPaginationReached) null else offset + entities.size

            localDataSource.applyPagedRemoteResponse(
                isRefresh = loadType == LoadType.REFRESH,
                entities = entities,
                nextOffset = nextOffset,
            )

            Log.d(TAG_DATABASE, "AnimeRemoteMediator: load=$loadType offset=$offset fetched=${entities.size} nextOffset=$nextOffset end=$endOfPaginationReached")

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (throwable: Throwable) {
            Log.e(TAG_DATABASE, "AnimeRemoteMediator: load failed", throwable)
            MediatorResult.Error(throwable)
        }
    }

    private fun AnimeDTO.hasNextPage(): Boolean = !links?.next.isNullOrBlank()
}