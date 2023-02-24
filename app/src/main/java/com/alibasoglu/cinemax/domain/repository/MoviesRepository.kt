package com.alibasoglu.cinemax.domain.repository

import androidx.paging.Pager
import com.alibasoglu.cinemax.domain.model.Movie


interface MoviesRepository {

    fun getMoviesPager(): Pager<Int, Movie>
}
