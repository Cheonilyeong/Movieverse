package com.ilyeong.movieverse.data

import retrofit2.http.GET

interface AuthApiService {

    @GET
    suspend fun createRequestToken(): RequestToken
}