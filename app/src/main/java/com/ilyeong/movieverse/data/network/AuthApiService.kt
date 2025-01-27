package com.ilyeong.movieverse.data.network

import com.ilyeong.movieverse.data.model.RequestTokenResponse
import com.ilyeong.movieverse.data.model.SessionIdRequest
import com.ilyeong.movieverse.data.model.SessionIdResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    @GET("authentication/token/new")
    suspend fun createRequestToken(): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSessionId(@Body requestToken: SessionIdRequest): SessionIdResponse
}