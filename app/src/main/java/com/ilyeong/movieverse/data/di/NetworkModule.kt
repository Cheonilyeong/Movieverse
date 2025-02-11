package com.ilyeong.movieverse.data.di

import com.ilyeong.movieverse.BuildConfig
import com.ilyeong.movieverse.data.di.qualifier.BaseInterceptor
import com.ilyeong.movieverse.data.di.qualifier.BaseOkHttpClient
import com.ilyeong.movieverse.data.di.qualifier.SessionInterceptor
import com.ilyeong.movieverse.data.di.qualifier.SessionOkHttpClient
import com.ilyeong.movieverse.data.local.SessionIdLocalDataSource
import com.ilyeong.movieverse.data.network.AuthApiService
import com.ilyeong.movieverse.data.network.MovieApiService
import com.ilyeong.movieverse.data.network.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @BaseInterceptor
    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor =
        Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("accept", "application/json")
                .header("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                .build()
            chain.proceed(newRequest)
        }

    @BaseOkHttpClient
    @Provides
    @Singleton
    fun provideBaseOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @BaseInterceptor authInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @SessionInterceptor
    @Provides
    @Singleton
    fun provideSessionInterceptor(
        sessionIdLocalDataSource: SessionIdLocalDataSource
    ): Interceptor =
        Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val sessionId = runBlocking { sessionIdLocalDataSource.getSessionId() }

            val newUrl = originalUrl.newBuilder()
                .addQueryParameter("session_id", sessionId)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .header("accept", "application/json")
                .header("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                .build()
            chain.proceed(newRequest)
        }

    @SessionOkHttpClient
    @Provides
    @Singleton
    fun provideSessionOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @SessionInterceptor movieInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(movieInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideBaseApiService(
        json: Json,
        @BaseOkHttpClient okHttpClient: OkHttpClient
    ): AuthApiService =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(okHttpClient)
            .build()
            .create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideMovieApiService(
        json: Json,
        @BaseOkHttpClient okHttpClient: OkHttpClient
    ): MovieApiService =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(okHttpClient)
            .build()
            .create(MovieApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApiService(
        json: Json,
        @SessionOkHttpClient okHttpClient: OkHttpClient
    ): UserApiService =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/account/${BuildConfig.ACCOUNT_ID}/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(okHttpClient)
            .build()
            .create(UserApiService::class.java)
}