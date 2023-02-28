package com.alibasoglu.cinemax.moviedetail.data

import com.alibasoglu.cinemax.moviedetail.data.remote.MovieDetailApi
import com.alibasoglu.cinemax.moviedetail.data.remote.model.mapToMovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.MovieDetailRepository
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieDetailRepositoryImpl(
    private val movieDetailApi: MovieDetailApi
) : MovieDetailRepository {

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetail>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                movieDetailApi.getMovieDetails(movieId).body()
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            }
            response?.let { movieDetailsResponse ->
                val movieDetail = movieDetailsResponse.mapToMovieDetail()
                emit(Resource.Success(data = movieDetail))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }
}
