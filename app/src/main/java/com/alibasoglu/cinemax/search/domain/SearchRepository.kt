package com.alibasoglu.cinemax.search.domain

import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.search.ui.model.PersonItem
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchPerson(searchQuery: String): Flow<Resource<List<PersonItem>>>

    suspend fun getRecommendedMovies(movieId: Int): Flow<Resource<List<Movie>>>

    suspend fun getMovieOfTheDay(): Flow<Resource<Movie>>
}
