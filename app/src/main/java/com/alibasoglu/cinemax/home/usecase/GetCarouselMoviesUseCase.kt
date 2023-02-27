package com.alibasoglu.cinemax.home.usecase

import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetCarouselMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Movie>>> {
        return moviesRepository.getUpcomingMovies()
    }
}
