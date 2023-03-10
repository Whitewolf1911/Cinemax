package com.alibasoglu.cinemax.data

import android.content.SharedPreferences
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alibasoglu.cinemax.ImagesConfigData
import com.alibasoglu.cinemax.data.local.MovieDatabase
import com.alibasoglu.cinemax.data.local.model.mapToMovieDetail
import com.alibasoglu.cinemax.data.local.model.mapToTvShowDetail
import com.alibasoglu.cinemax.data.remote.MoviesApi
import com.alibasoglu.cinemax.data.remote.model.mapToMovie
import com.alibasoglu.cinemax.data.remote.pagingsource.MoviesPagingSource
import com.alibasoglu.cinemax.data.remote.pagingsource.PagingDataType
import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.mapToMovieEntity
import com.alibasoglu.cinemax.moviedetail.domain.model.mapToShowEntity
import com.alibasoglu.cinemax.search.data.SearchApi
import com.alibasoglu.cinemax.setConfigDataFromResponse
import com.alibasoglu.cinemax.utils.ENGLISH
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val searchApi: SearchApi,
    private val movieDatabase: MovieDatabase,
    private val sharedPreferences: SharedPreferences
) : MoviesRepository {

    private val movieDao = movieDatabase.dao

    override fun getMoviesPager(pagingDataType: PagingDataType<Any>): Pager<Int, Movie> = Pager(
        config = PagingConfig(
            pageSize = MoviesPagingSource.MOVIES_PAGE_SIZE,
            initialLoadSize = MoviesPagingSource.MOVIES_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            MoviesPagingSource(
                moviesApi = moviesApi,
                searchApi,
                pagingDataType = pagingDataType,
                sharedPreferences = sharedPreferences
            )
        }
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
        val currentLanguage = sharedPreferences.getString("locale", ENGLISH) ?: ENGLISH
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                moviesApi.getUpcomingMovies(page = 1, language = currentLanguage).body()
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

    override suspend fun insertMovieToDatabase(movieDetail: MovieDetail) {
        movieDao.insertMovie(movieDetail.mapToMovieEntity())
    }

    override suspend fun getWishListedMovies(): Flow<List<MovieDetail>> {
        return flow {
            emit(movieDao.getWishListedMovies().map {
                it.mapToMovieDetail()
            })
        }
    }

    override suspend fun getWishListedShows(): Flow<List<TvShowDetail>> {
        return flow {
            emit(movieDao.getWishListedShows().map {
                it.mapToTvShowDetail()
            })
        }
    }

    override suspend fun insertShowToDatabase(showDetail: TvShowDetail) {
        movieDao.insertShow(showDetail.mapToShowEntity())
    }

    override suspend fun checkIsMovieWishListed(movieId: Int): Boolean {
        return movieDao.checkMovieExists(movieId)
    }

    override suspend fun checkIsShowWishListed(showId: Int): Boolean {
        return movieDao.checkShowExists(showId)
    }

    override suspend fun removeShowFromDatabase(showId: Int) {
        movieDao.deleteShowEntity(showId)
    }

    override suspend fun removeMovieFromDatabase(movieId: Int) {
        movieDao.deleteMovieEntity(movieId)
    }

    override suspend fun getRandomWishListedMovieId(): Int {
        return try {
            val wishList = movieDao.getWishListedMovies()
            wishList.random().id
        } catch (e: NoSuchElementException) {
            // If no wishListed item return the id of Lord of the Rings
            122
        }
    }
}
