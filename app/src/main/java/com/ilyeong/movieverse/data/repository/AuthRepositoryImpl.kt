package com.ilyeong.movieverse.data.repository

import android.util.Log
import com.ilyeong.movieverse.data.local.SessionIdLocalDataSource
import com.ilyeong.movieverse.data.model.SessionIdRequest
import com.ilyeong.movieverse.data.model.toDomain
import com.ilyeong.movieverse.data.network.AuthApiService
import com.ilyeong.movieverse.domain.model.RequestToken
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val sessionIdLocalDataSource: SessionIdLocalDataSource
) : AuthRepository {

    override fun createRequestToken() = flow<RequestToken> {
        val requestToken = apiService.createRequestToken().toDomain()
        emit(requestToken)
    }

    override fun createSessionId(requestToken: String) = flow<Unit> {
        val sessionIdResponse = apiService.createSessionId(SessionIdRequest(requestToken))
        require(sessionIdResponse.success) { "알 수 없는 오류가 발생했습니다." }
        sessionIdLocalDataSource.saveSessionId(sessionIdResponse.sessionId)
        emit(Unit)
        Log.d("createSessionId", "createSessionId: $sessionIdResponse")
    }
}