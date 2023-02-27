package com.alibasoglu.cinemax.data.remote

import com.alibasoglu.cinemax.data.remote.model.ConfigurationResponse
import com.alibasoglu.cinemax.data.remote.model.PopularMoviesResponse
import com.alibasoglu.cinemax.data.remote.model.UpcomingMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<PopularMoviesResponse>

    @GET("configuration")
    suspend fun getConfigurationData(): Response<ConfigurationResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): Response<UpcomingMoviesResponse>
}
