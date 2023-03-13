package com.alibasoglu.cinemax.moviedetail.ui.model

import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail

data class TvShowDetailState(
    val tvShowDetail: TvShowDetail? = null,
    val isLoading: Boolean = false
)
