package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.domain.repository.MoviesRepository

class SetImagesConfigDataUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke() = moviesRepository.getSetConfigurationData()
}
