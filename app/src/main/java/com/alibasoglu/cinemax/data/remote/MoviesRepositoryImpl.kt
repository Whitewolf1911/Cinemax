package com.alibasoglu.cinemax.data.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.data.remote.pagingsource.MoviesPagingSource
import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.setConfigDataFromResponse

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
                    Log.d("mylog", data.toString())
                    ImagesConfigData.setConfigDataFromResponse(it)
                }
            }
        } catch (e: Exception) {
            Log.d("Exception Occurred", e.toString())
        }
    }
}
