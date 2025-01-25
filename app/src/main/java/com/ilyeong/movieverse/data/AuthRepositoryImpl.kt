package com.ilyeong.movieverse.data

import com.ilyeong.movieverse.RequestToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService
) : AuthRepository {

    override suspend fun createRequestToken(): RequestToken {
        return apiService.createRequestToken()
    }
}