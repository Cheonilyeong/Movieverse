package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.AuthApiService
import com.ilyeong.movieverse.domain.model.RequestToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService
) : AuthRepository {

    override suspend fun createRequestToken(): RequestToken {
        return apiService.createRequestToken().toDomain()
    }
}