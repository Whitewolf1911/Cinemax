package com.alibasoglu.cinemax.domain.usecase

import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.moviedetail.domain.model.mapToWishListCardItem
import com.alibasoglu.cinemax.ui.model.WishListCardItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWishListedMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): Flow<List<WishListCardItem>> {
        return moviesRepository.getWishListedMovies().map { list ->
            list.map {
                it.mapToWishListCardItem()
            }
        }
    }
}
