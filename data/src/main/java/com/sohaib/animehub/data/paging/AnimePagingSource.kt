package com.sohaib.animehub.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sohaib.animehub.core.common.constants.Constants.TAG
import com.sohaib.animehub.core.network.apis.AnimeApiService
import com.sohaib.animehub.core.network.models.animeChild.Data

class AnimePagingSource(private val animeApiService: AnimeApiService) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {

            val offset = params.key ?: 0
            val limit = params.loadSize

            val response = animeApiService.getAnimeList(
                limit = limit,
                offset = offset
            )

            LoadResult.Page(
                data = response.data,
                prevKey = if (offset == 0) null else (offset - limit),
                nextKey = if (offset == response.meta?.count) null else (offset + limit)
            )
        } catch (ex: Exception) {
            Log.e(TAG, "AnimePagingSource: load: Exception: ", ex)
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(state.config.pageSize)
        }
    }
}