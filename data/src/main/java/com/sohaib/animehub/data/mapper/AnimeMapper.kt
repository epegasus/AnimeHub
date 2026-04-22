package com.sohaib.animehub.data.mapper

import com.sohaib.animehub.core.network.models.AnimeDTO
import com.sohaib.animehub.domain.models.Anime

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
                ?: "",
            smallImageUrl = attributes?.posterImage?.small.orEmpty(),
        )
    }
}