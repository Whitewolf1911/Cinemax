package com.alibasoglu.cinemax.data.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.data.remote.model.mapToMovie
import com.alibasoglu.cinemax.data.remote.pagingsource.MoviesPagingSource
import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.setConfigDataFromResponse
import com.alibasoglu.cinemax.utils.Resource
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi
) : MoviesRepository {
    override fun getMoviesPager(): Pager<Int, Movie> = Pager(
        config = PagingConfig(
            pageSize = MoviesPagingSource.MOVIES_PAGE_SIZE,
            initialLoadSize = MoviesPagingSource.MOVIES_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviesPagingSource(moviesApi = moviesApi) }
    )

    override suspend fun getSetConfigurationData() {
        try {
            val result = moviesApi.getConfigurationData()
            if (result.isSuccessful) {
                val data = result.body()?.images
                data?.let {
                    ImagesConfigData.setConfigDataFromResponse(it)
                }
            }
        } catch (e: Exception) {
            Log.d("Exception Occurred", e.toString())
        }
    }

    override suspend fun getUpcomingMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                moviesApi.getUpcomingMovies(page = 1).body()
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.toString()))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            }
            response?.let { upcomingMoviesResponse ->
                val movies = upcomingMoviesResponse.results.subList(0, 3).map {
                    it.mapToMovie()
                }
                emit(Resource.Success(data = movies))
                emit(Resource.Loading(isLoading = false))
            }

        }
    }
}
