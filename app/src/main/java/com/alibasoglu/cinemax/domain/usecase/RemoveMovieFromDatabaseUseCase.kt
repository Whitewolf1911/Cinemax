package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.domain.repository.MoviesRepository

class RemoveMovieFromDatabaseUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int) = moviesRepository.removeMovieFromDatabase(movieId)
}
