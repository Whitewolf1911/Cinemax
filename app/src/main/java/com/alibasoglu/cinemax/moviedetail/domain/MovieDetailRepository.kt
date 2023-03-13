package com.alibasoglu.cinemax.moviedetail.domain

import com.alibasoglu.cinemax.moviedetail.data.remote.model.tv.Episode
import com.alibasoglu.cinemax.moviedetail.domain.model.CastCrewPerson
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.Trailer
import com.alibasoglu.cinemax.moviedetail.domain.model.TvShowDetail
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {

    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetail>>

    suspend fun getMovieCastCrew(movieId: Int): Flow<Resource<List<CastCrewPerson>>>

    suspend fun getMovieTrailers(movieId: Int): Flow<Resource<List<Trailer>>>

    suspend fun getTvShowDetails(showId: Int): Flow<Resource<TvShowDetail>>

    suspend fun getTvShowCastCrew(showId: Int): Flow<Resource<List<CastCrewPerson>>>

    suspend fun getTvShowSeasonEpisodes(showId: Int, seasonNumber: Int): Flow<Resource<List<Episode>>>
}
