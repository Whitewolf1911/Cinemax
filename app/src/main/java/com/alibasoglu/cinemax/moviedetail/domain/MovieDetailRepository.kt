package com.alibasoglu.cinemax.moviedetail.domain

import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {

    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetail>>
}
