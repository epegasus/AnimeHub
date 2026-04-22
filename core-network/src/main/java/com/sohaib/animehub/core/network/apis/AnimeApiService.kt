package com.sohaib.animehub.core.network.apis

import com.sohaib.animehub.core.network.models.AnimeDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {

    @GET("anime")
    suspend fun getAnimeList(
        @Query("page[limit]") limit: Int = 20,
        @Query("page[offset]") offset: Int = 0,
    ): AnimeDTO

}