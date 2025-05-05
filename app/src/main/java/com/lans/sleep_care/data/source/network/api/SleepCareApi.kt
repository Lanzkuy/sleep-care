package com.lans.sleep_care.data.source.network.api

import com.lans.sleep_care.data.source.network.dto.request.LoginRequest
import com.lans.sleep_care.data.source.network.dto.request.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.LoginResponse
import com.lans.sleep_care.data.source.network.dto.response.MeResponse
import com.lans.sleep_care.data.source.network.dto.response.RegisterResponse
import com.lans.sleep_care.data.source.network.dto.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SleepCareApi {
    @POST("login")
    suspend fun login(
        @Body requestBody: LoginRequest
    ): ApiResponse<LoginResponse>

    @POST("register")
    suspend fun register(
        @Body requestBody: RegisterRequest
    ): ApiResponse<RegisterResponse>

    @GET("patient")
    suspend fun me(): ApiResponse<MeResponse>
}