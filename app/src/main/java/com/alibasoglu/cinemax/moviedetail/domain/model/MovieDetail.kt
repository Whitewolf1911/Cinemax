package com.alibasoglu.cinemax.moviedetail.domain.model

data class MovieDetail(
    val backdrop_path: String,
    val genre: String,
    val id: Int,
    val original_title: String,
    val overview: String?,
    val poster_path: String?,
    val release_date: String,
    val runtime: Int,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
)
