package com.alibasoglu.cinemax.search.domain.usecase

import com.alibasoglu.cinemax.search.domain.SearchRepository

class GetMovieOfTheDayUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke() = searchRepository.getMovieOfTheDay()
}
