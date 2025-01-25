package com.ilyeong.movieverse.data.di

import com.ilyeong.movieverse.BuildConfig
import com.ilyeong.movieverse.data.network.AuthApiService
import com.ilyeong.movieverse.data.repository.AuthRepository
import com.ilyeong.movieverse.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @Provides
    fun provideAuthInterceptor(): Interceptor =
        Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("accept", "application/json")
                .header("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                .build()
            chain.proceed(newRequest)
        }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    fun provideAuthApuService(
        okHttpClient: OkHttpClient
    ): AuthApiService =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .client(okHttpClient)
            .build()
            .create(AuthApiService::class.java)

    @Provides
    fun provideAuthRepository(apiService: AuthApiService): AuthRepository =
        AuthRepositoryImpl(apiService)

}