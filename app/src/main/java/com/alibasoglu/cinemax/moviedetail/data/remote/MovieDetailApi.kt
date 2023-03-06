package com.alibasoglu.cinemax.moviedetail.data.remote

import com.alibasoglu.cinemax.moviedetail.data.remote.model.MovieCreditsResponse
import com.alibasoglu.cinemax.moviedetail.data.remote.model.MovieDetailsResponse
import com.alibasoglu.cinemax.moviedetail.data.remote.model.MovieTrailersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailApi {

    @GET("movie/{movie_Id}")
    suspend fun getMovieDetails(
        @Path("movie_Id") movieId: Int
    ): Response<MovieDetailsResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): Response<MovieCreditsResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path("movie_id") movieId: Int
    ): Response<MovieTrailersResponse>
}
