package com.alibasoglu.cinemax.data.repository

import androidx.paging.Pager
import com.alibasoglu.cinemax.data.remote.pagingsource.PagingDataType
import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository : MoviesRepository {

    private val wishListedMovies = mutableListOf<MovieDetail>()

    private val wishListedShows = mutableListOf<TvShowDetail>()

    override fun getMoviesPager(pagingDataType: PagingDataType<Any>): Pager<Int, Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getSetConfigurationData() {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(): Flow<Resource<List<Movie>>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovieToDatabase(movieDetail: MovieDetail) {
        wishListedMovies.add(movieDetail)
    }

    override suspend fun getWishListedMovies(): Flow<List<MovieDetail>> {
        return flow { emit(wishListedMovies) }
    }

    override suspend fun getRandomWishListedMovieId(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getWishListedShows(): Flow<List<TvShowDetail>> {
        return flow { emit(wishListedShows) }
    }

    override suspend fun insertShowToDatabase(showDetail: TvShowDetail) {
        wishListedShows.add(showDetail)
    }

    override suspend fun checkIsMovieWishListed(movieId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun checkIsShowWishListed(showId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun removeShowFromDatabase(showId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun removeMovieFromDatabase(movieId: Int) {
        TODO("Not yet implemented")
    }
}
