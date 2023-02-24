package com.alibasoglu.cinemax.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alibasoglu.cinemax.data.remote.pagingsource.MoviesPagingSource
import com.alibasoglu.cinemax.domain.model.Movie
import com.alibasoglu.cinemax.domain.repository.MoviesRepository

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
}
