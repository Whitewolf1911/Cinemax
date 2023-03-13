package com.alibasoglu.cinemax.moviedetail.data.remote.model.tv

data class SeasonResponse(
    val _id: String,
    val air_date: String,
    val episodes: List<Episode>,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int
)
