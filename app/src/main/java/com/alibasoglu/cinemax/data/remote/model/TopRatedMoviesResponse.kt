package com.alibasoglu.cinemax.data.remote.model

data class TopRatedMoviesResponse(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)
