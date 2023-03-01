package com.alibasoglu.cinemax.search.di

import com.alibasoglu.cinemax.search.data.SearchApi
import com.alibasoglu.cinemax.search.data.SearchRepositoryImpl
import com.alibasoglu.cinemax.search.domain.SearchRepository
import com.alibasoglu.cinemax.search.domain.usecase.SearchPersonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(searchApi: SearchApi): SearchRepository {
        return SearchRepositoryImpl(searchApi)
    }

    @Provides
    @Singleton
    fun provideSearchPersonUseCase(searchRepository: SearchRepository) = SearchPersonUseCase(searchRepository)


}
