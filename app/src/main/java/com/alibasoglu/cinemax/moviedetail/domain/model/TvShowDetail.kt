package com.alibasoglu.cinemax.moviedetail.domain.model

data class TvShowDetail(
    val backdrop_path: String?,
    val episode_run_time: Int,
    val first_air_date: String,
    val genres: String,
    val id: Int,
    val name: String,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val original_name: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double,
)
