package com.sohaib.animehub.data.mapper

import com.sohaib.animehub.core.network.models.AnimeDTO
import com.sohaib.animehub.core.network.models.AnimeDetailDTO
import com.sohaib.animehub.domain.models.Anime
import com.sohaib.animehub.domain.models.AnimeDetail

fun AnimeDTO.toDomain(): List<Anime> {
    return data.map { item ->
        val attributes = item.attributes
        val titles = attributes?.titles
        Anime(
            id = item.id.orEmpty(),
            title = titles?.en
                ?: titles?.en_us
                ?: titles?.en_jp
                ?: titles?.ja_jp
                ?: attributes?.canonicalTitle
                    .orEmpty(),
            // Some responses return http image URLs; normalize to https for Android network security.
            smallImageUrl = attributes?.posterImage?.small.orEmpty(),
        )
    }
}

fun AnimeDetailDTO.toDomain(): AnimeDetail {
    val attributes = data.attributes
    val titles = attributes?.titles
    return AnimeDetail(
        id = data.id.orEmpty(),
        title = titles?.en
            ?: titles?.en_us
            ?: titles?.en_jp
            ?: titles?.ja_jp
            ?: attributes?.canonicalTitle
                .orEmpty(),
        slug = attributes?.slug.orEmpty(),
        coverImageLargeUrl = attributes?.coverImage?.large.orEmpty(),
        posterImageLargeUrl = attributes?.posterImage?.large.orEmpty(),
        description = attributes?.description.orEmpty(),
        episodeCount = attributes?.episodeCount ?: 0,
        youtubeVideoId = attributes?.youtubeVideoId.orEmpty(),
        createdAt = attributes?.createdAt.orEmpty(),
        updatedAt = attributes?.updatedAt.orEmpty()
    )
}