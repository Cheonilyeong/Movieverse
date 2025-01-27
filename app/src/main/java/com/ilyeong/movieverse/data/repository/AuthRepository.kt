package com.ilyeong.movieverse.data.repository

import com.ilyeong.movieverse.domain.model.RequestToken

interface AuthRepository {

    suspend fun createRequestToken(): RequestToken
    suspend fun createSessionId(requestToken: String)
}