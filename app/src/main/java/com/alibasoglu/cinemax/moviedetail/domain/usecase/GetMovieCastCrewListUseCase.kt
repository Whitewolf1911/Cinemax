package com.alibasoglu.cinemax.moviedetail.domain.usecase

import com.alibasoglu.cinemax.moviedetail.domain.MovieDetailRepository

class GetMovieCastCrewListUseCase(
    private val movieDetailRepository: MovieDetailRepository
) {

    suspend operator fun invoke(movieId: Int) = movieDetailRepository.getMovieCastCrew(movieId)
}
