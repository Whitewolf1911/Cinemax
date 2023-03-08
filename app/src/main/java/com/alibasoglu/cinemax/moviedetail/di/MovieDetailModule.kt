package com.alibasoglu.cinemax.moviedetail.di

import android.content.SharedPreferences
import com.alibasoglu.cinemax.moviedetail.data.MovieDetailRepositoryImpl
import com.alibasoglu.cinemax.moviedetail.data.remote.MovieDetailApi
import com.alibasoglu.cinemax.moviedetail.domain.MovieDetailRepository
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieCastCrewListUseCase
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieDetailsUseCase
import com.alibasoglu.cinemax.moviedetail.domain.usecase.GetMovieTrailerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieDetailModule {

    @Provides
    @Singleton
    fun provideMovieDetailApi(retrofit: Retrofit): MovieDetailApi {
        return retrofit.create(MovieDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        movieDetailApi: MovieDetailApi,
        sharedPreferences: SharedPreferences
    ): MovieDetailRepository {
        return MovieDetailRepositoryImpl(
            movieDetailApi = movieDetailApi,
            sharedPreferences = sharedPreferences
        )
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(movieDetailsRepository: MovieDetailRepository) =
        GetMovieDetailsUseCase(movieDetailsRepository)

    @Provides
    @Singleton
    fun provideGetMovieCastCrewListUseCase(movieDetailsRepository: MovieDetailRepository) =
        GetMovieCastCrewListUseCase(movieDetailsRepository)

    @Provides
    @Singleton
    fun provideGetMovieTrailerUseCase(movieDetailsRepository: MovieDetailRepository) =
        GetMovieTrailerUseCase(movieDetailsRepository)
}
