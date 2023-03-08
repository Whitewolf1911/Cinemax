package com.alibasoglu.cinemax.moviedetail.data

import android.content.SharedPreferences
import com.alibasoglu.cinemax.moviedetail.data.remote.MovieDetailApi
import com.alibasoglu.cinemax.moviedetail.data.remote.model.mapToCastCrewPerson
import com.alibasoglu.cinemax.moviedetail.data.remote.model.mapToMovieDetail
import com.alibasoglu.cinemax.moviedetail.data.remote.model.mapToTrailer
import com.alibasoglu.cinemax.moviedetail.domain.MovieDetailRepository
import com.alibasoglu.cinemax.moviedetail.domain.model.CastCrewPerson
import com.alibasoglu.cinemax.moviedetail.domain.model.MovieDetail
import com.alibasoglu.cinemax.moviedetail.domain.model.Trailer
import com.alibasoglu.cinemax.utils.ENGLISH
import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MovieDetailRepositoryImpl(
    private val movieDetailApi: MovieDetailApi,
    private val sharedPreferences: SharedPreferences
) : MovieDetailRepository {

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetail>> {
       val currentLanguage = sharedPreferences.getString("locale", ENGLISH) ?: ENGLISH
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                movieDetailApi.getMovieDetails(movieId = movieId, language = currentLanguage).body()
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            }
            response?.let { movieDetailsResponse ->
                val movieDetail = movieDetailsResponse.mapToMovieDetail()
                emit(Resource.Success(data = movieDetail))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getMovieCastCrew(movieId: Int): Flow<Resource<List<CastCrewPerson>>> {
        val currentLanguage = sharedPreferences.getString("locale", ENGLISH) ?: ENGLISH
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                movieDetailApi.getMovieCredits(movieId = movieId, language = currentLanguage).body()
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            }
            response?.let { creditsResponse ->
                val castCrewList: MutableList<CastCrewPerson> = mutableListOf()
                val castList = creditsResponse.cast.map {
                    it.mapToCastCrewPerson()
                }
                val crewList = creditsResponse.crew.map {
                    it.mapToCastCrewPerson()
                }
                castCrewList.addAll(castList)
                castCrewList.addAll(crewList)
                emit(Resource.Success(data = castCrewList))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getMovieTrailers(movieId: Int): Flow<Resource<List<Trailer>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val response = try {
                movieDetailApi.getMovieTrailers(movieId).body()
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            } catch (e: IOException) {
                emit(Resource.Error(message = e.toString()))
                null
            }
            response?.results?.let { movieTrailersResults ->
                val movieTrailers = movieTrailersResults.map { it.mapToTrailer() }
                emit(Resource.Success(data = movieTrailers))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }
}
