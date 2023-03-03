package com.alibasoglu.cinemax.search.data

import com.alibasoglu.cinemax.data.remote.model.mapToMovie
import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.search.data.model.mapToPersonItem
import com.alibasoglu.cinemax.search.domain.SearchRepository
import com.alibasoglu.cinemax.search.ui.model.PersonItem
import com.alibasoglu.cinemax.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class SearchRepositoryImpl(
    private val searchApi: SearchApi
) : SearchRepository {

    override suspend fun searchPerson(searchQuery: String): Flow<Resource<List<PersonItem>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                searchApi.searchPerson(searchQuery).body()
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.toString()))
                null
            }
            response?.results?.let { personResultList ->
                val personItemList = personResultList.map {
                    it.mapToPersonItem()
                }
                emit(Resource.Success(data = personItemList))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getRecommendedMovies(movieId: Int): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                searchApi.getRecommendedMovies(movieId).body()
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.toString()))
                null
            }
            response?.results?.let { movieResultList ->
                val movieList = movieResultList.map {
                    it.mapToMovie()
                }
                emit(Resource.Success(data = movieList))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getMovieOfTheDay(): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                searchApi.getNowPlayingMovies().body()
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.toString()))
                null
            }
            response?.results?.let { movieResultList ->
                if (movieResultList.isNotEmpty()) {
                    val movie = movieResultList.random().mapToMovie()
                    emit(Resource.Success(data = movie))
                    emit(Resource.Loading(isLoading = false))
                }
            }
        }
    }
}
