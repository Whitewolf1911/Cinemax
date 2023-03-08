package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.data.remote.pagingsource.PagingDataType
import com.alibasoglu.cinemax.domain.repository.MoviesRepository

class GetMoviesPagerUseCase(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(pagingDataType: PagingDataType<Any>) = moviesRepository.getMoviesPager(pagingDataType)
}
