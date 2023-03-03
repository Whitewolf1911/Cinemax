package com.alibasoglu.cinemax.search.data.model

import com.alibasoglu.cinemax.data.remote.model.MovieResult

class NowPlayingMoviesResponse(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)
