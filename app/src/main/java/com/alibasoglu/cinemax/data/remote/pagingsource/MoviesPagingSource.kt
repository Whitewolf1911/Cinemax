package com.alibasoglu.cinemax.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alibasoglu.cinemax.data.remote.MoviesApi
import com.alibasoglu.cinemax.data.remote.model.mapToMovie
import com.alibasoglu.cinemax.domain.model.Movie
import java.io.IOException
import retrofit2.HttpException

class MoviesPagingSource(
    private val moviesApi: MoviesApi
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { closestPage ->
                closestPage.prevKey?.plus(1) ?: closestPage.nextKey?.minus(1)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: FIRST_PAGE_INDEX
        return try {
            val moviesApiResponse = moviesApi.getPopularMovies(page = page).body()?.results?.map {
                it.mapToMovie()
            }

            val moviesList = moviesApiResponse.orEmpty()
            val nextKey = if (moviesList.isEmpty()) null else page.plus(1)
            val prevKey = if (page == FIRST_PAGE_INDEX) null else page.minus(1)
            LoadResult.Page(
                data = moviesList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val MOVIES_PAGE_SIZE = 10
        private const val FIRST_PAGE_INDEX = 1
    }
}
