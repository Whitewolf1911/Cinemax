package com.alibasoglu.cinemax.search.data

import com.alibasoglu.cinemax.search.data.model.NowPlayingMoviesResponse
import com.alibasoglu.cinemax.search.data.model.PersonSearchResult
import com.alibasoglu.cinemax.search.data.model.RecommendedMoviesResponse
import com.alibasoglu.cinemax.search.data.model.SearchMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {

    @GET("search/person")
    suspend fun searchPerson(
        @Query("query") searchQuery: String
    ): Response<PersonSearchResult>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") searchQuery: String,
        @Query("page") page: Int
    ): Response<SearchMovieResponse>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: Int
    ): Response<RecommendedMoviesResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlayingMoviesResponse>

}
