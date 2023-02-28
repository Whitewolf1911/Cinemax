package com.alibasoglu.cinemax.moviedetail.ui.model

import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail

data class MovieDetailState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false
)
