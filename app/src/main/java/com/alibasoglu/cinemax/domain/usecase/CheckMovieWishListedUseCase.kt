package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.domain.repository.MoviesRepository

class CheckMovieWishListedUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int) = moviesRepository.checkIsMovieWishListed(movieId)
}
