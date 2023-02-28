package com.alibasoglu.cinemax.moviedetail.data.remote.model

data class MovieCreditsResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)
