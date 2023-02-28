package com.alibasoglu.cinemax.di

import com.alibasoglu.cinemax.BuildConfig
import com.alibasoglu.cinemax.data.remote.MoviesApi
import com.alibasoglu.cinemax.data.remote.MoviesRepositoryImpl
import com.alibasoglu.cinemax.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val headerApiKey = BuildConfig.MOVIES_HEADER_API_KEY
        val headerInterceptor = Interceptor { chain ->
            chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer $headerApiKey")
                        .addHeader("Content-Type", "application/json;charset=utf-8")
                        .build()
                )
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesApi: MoviesApi): MoviesRepository {
        return MoviesRepositoryImpl(moviesApi = moviesApi)
    }

}
