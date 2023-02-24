package com.alibasoglu.cinemax.data.remote.model

import com.alibasoglu.cinemax.domain.model.Movie

data class MovieResult(
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieResult.mapToMovie(): Movie {
    return Movie(
        id = id,
        backdrop_path = backdrop_path ?: "",
        genre_ids = genre_ids,
        original_title = original_title,
        overview = overview,
        poster_path = poster_path ?: "",
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average
    )
}
