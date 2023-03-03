package com.alibasoglu.cinemax.moviedetail.di

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
import javax.inject.Singleton
import retrofit2.Retrofit


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
    fun provideMovieDetailRepository(movieDetailApi: MovieDetailApi): MovieDetailRepository {
        return MovieDetailRepositoryImpl(movieDetailApi)
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
