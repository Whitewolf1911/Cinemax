package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.domain.repository.MoviesRepository

class GetMoviesPagerUseCase(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(searchQuery: String?) = moviesRepository.getMoviesPager(searchQuery)
}
