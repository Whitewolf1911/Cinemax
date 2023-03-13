package com.alibasoglu.cinemax.moviedetail.data.remote.model.tv

import com.alibasoglu.cinemax.model.Genre
import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail

data class TvShowDetailResponse(
    val adult: Boolean,
    val backdrop_path: String?,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val name: String,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
)

fun TvShowDetailResponse.mapToTvShowDetail(): TvShowDetail {
    return TvShowDetail(
        backdrop_path = backdrop_path,
        episode_run_time = episode_run_time.firstOrNull() ?: 0,
        first_air_date = first_air_date,
        genres = genres.getOrNull(0)?.name ?: "",
        id = id,
        name = name,
        number_of_episodes = number_of_episodes,
        number_of_seasons = number_of_seasons,
        original_name = original_name,
        overview = overview,
        poster_path = poster_path,
        vote_average = vote_average
    )
}
