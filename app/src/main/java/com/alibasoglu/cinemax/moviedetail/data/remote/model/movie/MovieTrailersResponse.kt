package com.alibasoglu.cinemax.moviedetail.data.remote.model.movie

data class MovieTrailersResponse(
    val id: Int,
    val results: List<TrailerResult>
)
