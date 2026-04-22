package com.sohaib.animehub.data.dataSources.remote

import com.sohaib.animehub.core.network.apis.AnimeApiService
import com.sohaib.animehub.core.network.models.AnimeDTO

class AnimeRemoteDataSource(private val api: AnimeApiService) {

    suspend fun getAnimeList(): AnimeDTO = api.getAnimeList()

}