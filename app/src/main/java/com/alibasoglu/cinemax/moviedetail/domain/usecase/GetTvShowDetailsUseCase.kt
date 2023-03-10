package com.alibasoglu.cinemax.moviedetail.domain.usecase

import com.alibasoglu.cinemax.moviedetail.domain.MovieDetailRepository

class GetTvShowDetailsUseCase(
    private val movieDetailRepository: MovieDetailRepository
) {
    suspend operator fun invoke(showId: Int) = movieDetailRepository.getTvShowDetails(showId = showId)
}
