package com.alibasoglu.cinemax.search.domain.usecase

import com.alibasoglu.cinemax.search.domain.SearchRepository

class SearchPersonUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(searchQuery: String) = searchRepository.searchPerson(searchQuery)
}
