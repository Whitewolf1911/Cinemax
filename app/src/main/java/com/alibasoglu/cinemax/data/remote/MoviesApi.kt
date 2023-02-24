package com.alibasoglu.cinemax.data.remote

import com.alibasoglu.cinemax.data.remote.model.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<PopularMoviesResponse>

}
