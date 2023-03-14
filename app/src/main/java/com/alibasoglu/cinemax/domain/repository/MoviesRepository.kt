package com.alibasoglu.cinemax.domain.repository

import androidx.paging.Pager
import com.alibasoglu.cinemax.data.remote.pagingsource.PagingDataType
import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {

    fun getMoviesPager(pagingDataType: PagingDataType<Any>): Pager<Int, Movie>

    suspend fun getSetConfigurationData()

    //This function is for home screen carousel
    suspend fun getUpcomingMovies(): Flow<Resource<List<Movie>>>

    suspend fun insertMovieToDatabase(movieDetail: MovieDetail)

    suspend fun getWishListedMovies(): Flow<List<MovieDetail>>

    suspend fun getRandomWishListedMovieId(): Int

    suspend fun getWishListedShows(): Flow<List<TvShowDetail>>

    suspend fun insertShowToDatabase(showDetail: TvShowDetail)

    suspend fun checkIsMovieWishListed(movieId: Int): Boolean

    suspend fun checkIsShowWishListed(showId: Int): Boolean

    suspend fun removeShowFromDatabase(showId: Int)

    suspend fun removeMovieFromDatabase(movieId: Int)
}
