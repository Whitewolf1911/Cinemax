package com.alibasoglu.cinemax.auth.di

import com.alibasoglu.cinemax.auth.domain.AuthRepository
import com.alibasoglu.cinemax.auth.domain.usecase.LoginWithEmailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideLoginWithEmailUseCase(authRepository: AuthRepository): LoginWithEmailUseCase {
        return LoginWithEmailUseCase(authRepository)
    }

}
