package com.alibasoglu.cinemax.moviedetail.data.remote.model

data class TvShowCreditsResponse(
    val cast: List<CastTv>,
    val crew: List<CrewTv>,
    val id: Int
)
