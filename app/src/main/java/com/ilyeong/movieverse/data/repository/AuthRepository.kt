package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.domain.model.RequestToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun createRequestToken(): Flow<RequestToken>
    fun createSessionId(requestToken: String): Flow<Unit>
}