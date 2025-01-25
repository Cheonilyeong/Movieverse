package com.ilyeong.movieverse.data

interface AuthRepository {

    suspend fun createRequestToken(): com.ilyeong.movieverse.RequestToken
}