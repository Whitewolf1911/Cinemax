package com.alibasoglu.cinemax.moviedetail.domain

import com.alibasoglu.cinemax.moviedetail.domain.model.CastCrewPerson
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {

    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetail>>

    suspend fun getMovieCastCrew(movieId: Int): Flow<Resource<List<CastCrewPerson>>>
}
