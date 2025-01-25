package com.ilyeong.movieverse.data

import retrofit2.http.GET

interface AuthApiService {

    @GET("authentication/token/new")
    suspend fun createRequestToken(): RequestToken
}