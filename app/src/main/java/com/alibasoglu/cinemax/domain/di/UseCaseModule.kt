package com.alibasoglu.cinemax.domain.di

import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import com.alibasoglu.cinemax.domain.usecase.GetMoviesPagerUseCase
import com.alibasoglu.cinemax.domain.usecase.InsertMovieToDatabaseUseCase
import com.alibasoglu.cinemax.domain.usecase.SetImagesConfigDataUseCase
import com.alibasoglu.cinemax.home.usecase.GetCarouselMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetMoviesPagerUseCase(moviesRepository: MoviesRepository): GetMoviesPagerUseCase {
        return GetMoviesPagerUseCase(moviesRepository)
    }

    @Provides
    @Singleton
    fun provideSetImagesConfigDataUseCase(moviesRepository: MoviesRepository) =
        SetImagesConfigDataUseCase(moviesRepository)

    @Provides
    @Singleton
    fun provideGetCarouselMoviesUseCase(moviesRepository: MoviesRepository) = GetCarouselMoviesUseCase(moviesRepository)

    @Provides
    @Singleton
    fun provideInsertMovieToDatabaseUseCase(moviesRepository: MoviesRepository) =
        InsertMovieToDatabaseUseCase(moviesRepository)

}
