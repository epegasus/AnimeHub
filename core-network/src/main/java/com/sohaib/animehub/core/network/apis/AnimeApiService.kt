package com.sohaib.animehub.core.network.apis

import com.sohaib.animehub.core.network.models.AnimeDTO
import com.sohaib.animehub.core.network.models.AnimeDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiService {

    @GET("anime")
    suspend fun getAnimeList(
        @Query("page[limit]") limit: Int = 100,
        @Query("page[offset]") offset: Int = 0,
    ): AnimeDTO

    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") animeId: String): AnimeDetailDTO

}