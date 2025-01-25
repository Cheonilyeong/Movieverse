package com.ilyeong.movieverse.data

import com.ilyeong.movieverse.RequestToken

interface AuthApiService {

    suspend fun createRequestToken(): RequestToken
}