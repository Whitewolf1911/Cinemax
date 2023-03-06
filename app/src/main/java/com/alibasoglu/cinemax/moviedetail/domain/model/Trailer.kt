package com.alibasoglu.cinemax.moviedetail.domain.model

data class Trailer(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val published_at: String,
    val site: String,
    val type: String
)
