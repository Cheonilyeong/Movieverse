package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.data.local.SessionIdLocalDataSource
import com.ilyeong.movieverse.data.model.SessionIdRequest
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.AuthApiService
import com.ilyeong.movieverse.domain.model.RequestToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val sessionIdLocalDataSource: SessionIdLocalDataSource
) : AuthRepository {

    override suspend fun createRequestToken(): RequestToken {
        return apiService.createRequestToken().toDomain()
    }

    override suspend fun createSessionId(requestToken: String) {
        val sessionIdResponse = apiService.createSessionId(SessionIdRequest(requestToken))
        require(sessionIdResponse.success) { "알 수 없는 오류가 발생했습니다." }
        sessionIdLocalDataSource.saveSessionId(sessionIdResponse.sessionId)
    }
}