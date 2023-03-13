package com.alibasoglu.cinemax.search.data.model

data class MultiSearchResponse(
    val page: Int,
    val results: List<MovieOrTvResult>,
    val total_pages: Int,
    val total_results: Int
)
