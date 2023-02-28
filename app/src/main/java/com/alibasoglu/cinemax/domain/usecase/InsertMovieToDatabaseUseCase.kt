package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail

class InsertMovieToDatabaseUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieDetail: MovieDetail) = repository.insertMovieToDatabase(movieDetail)
}
