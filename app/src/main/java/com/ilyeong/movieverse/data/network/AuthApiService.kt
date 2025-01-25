package com.ilyeong.movieverse.data.network

import com.ilyeong.movieverse.data.model.RequestTokenResponse
import retrofit2.http.GET

interface AuthApiService {

    @GET("authentication/token/new")
    suspend fun createRequestToken(): RequestTokenResponse
}