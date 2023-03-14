package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail

class InsertShowToDatabaseUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(tvShowDetail: TvShowDetail) = moviesRepository.insertShowToDatabase(tvShowDetail)
}
