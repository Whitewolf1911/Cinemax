package com.alibasoglu.cinemax.moviedetail.domain.usecase

import com.alibasoglu.cinemax.moviedetail.domain.MovieDetailRepository

class GetEpisodesUseCase(
    private val movieDetailRepository: MovieDetailRepository
) {
    suspend operator fun invoke(seasonNumber: Int, showId: Int) =
        movieDetailRepository.getTvShowSeasonEpisodes(showId = showId, seasonNumber = seasonNumber)
}
