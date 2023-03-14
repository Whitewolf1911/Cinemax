package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.domain.repository.MoviesRepository

class CheckShowWishListedUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(showId: Int) = moviesRepository.checkIsShowWishListed(showId)
}
