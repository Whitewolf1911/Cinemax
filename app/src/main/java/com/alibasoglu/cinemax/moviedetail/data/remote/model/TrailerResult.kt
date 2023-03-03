package com.alibasoglu.cinemax.moviedetail.data.remote.model

import com.alibasoglu.cinemax.moviedetail.domain.model.Trailer

data class TrailerResult(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val published_at: String,
    val site: String,
    val size: Int,
    val type: String
)

fun TrailerResult.mapToTrailer(): Trailer {
    return Trailer(
        id = id,
        key = key,
        name = name,
        official = official,
        published_at = published_at,
        site = site,
        type = type
    )
}
