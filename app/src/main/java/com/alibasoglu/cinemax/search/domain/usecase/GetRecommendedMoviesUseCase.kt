package com.alibasoglu.cinemax.search.domain.usecase

import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.search.domain.SearchRepository
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetRecommendedMoviesUseCase(
    private val searchRepository: SearchRepository,
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Movie>>> {
        val randomMovieId = moviesRepository.getRandomWishListedMovieId()
        return searchRepository.getRecommendedMovies(randomMovieId)
    }
}
