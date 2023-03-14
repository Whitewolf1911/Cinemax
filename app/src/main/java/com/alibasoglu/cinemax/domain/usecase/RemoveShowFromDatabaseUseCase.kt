package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.domain.repository.MoviesRepository

class RemoveShowFromDatabaseUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(showId: Int) = moviesRepository.removeShowFromDatabase(showId)
}
