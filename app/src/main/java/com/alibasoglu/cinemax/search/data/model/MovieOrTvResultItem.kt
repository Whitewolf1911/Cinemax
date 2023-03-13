package com.alibasoglu.cinemax.search.data.model

import com.alibasoglu.cinemax.domain.model.Movie

data class MovieOrTvResult(
    val adult: Boolean,
    val backdrop_path: String?,
    val first_air_date: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val name: String?,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String?,
    val original_title: String?,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val video: Boolean?,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieOrTvResult.mapToMovie(): Movie {
    if (media_type == "movie") {
        return Movie(
            id = id,
            backdrop_path = backdrop_path ?: "",
            genre_ids = genre_ids,
            original_title = original_title ?: "",
            overview = overview,
            poster_path = poster_path ?: "",
            release_date = release_date ?: "",
            title = title,
            video = false,
            vote_average = vote_average,
            mediaType = "movie"
        )
    } else {
        return Movie(
            id = id,
            backdrop_path = backdrop_path ?: "",
            genre_ids = genre_ids,
            original_title = original_name ?: "",
            overview = overview,
            poster_path = poster_path ?: "",
            release_date = first_air_date ?: "",
            title = name ?: "",
            video = false,
            vote_average = vote_average,
            mediaType = "tv"
        )
    }

}
