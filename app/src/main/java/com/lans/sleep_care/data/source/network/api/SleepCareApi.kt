package com.lans.sleep_care.data.source.network.api

import com.lans.sleep_care.data.source.network.dto.request.ForgotPasswordRequest
import com.lans.sleep_care.data.source.network.dto.request.LoginRequest
import com.lans.sleep_care.data.source.network.dto.request.OtpRequest
import com.lans.sleep_care.data.source.network.dto.request.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.request.ResetPasswordRequest
import com.lans.sleep_care.data.source.network.dto.request.VerifyOtpRequest
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

    @POST("forgot-password")
    suspend fun forgotPassword(
        @Body requestBody: ForgotPasswordRequest
    ): ApiResponse<Any>

    @POST("reset-password")
    suspend fun resetPassword(
        @Body requestBody: ResetPasswordRequest
    ): ApiResponse<Any>

    @POST("otp/request")
    suspend fun requestOtp(
        @Body requestBody: OtpRequest
    ): ApiResponse<Any>

    @POST("otp/verify")
    suspend fun verifyOtp(
        @Body requestBody: VerifyOtpRequest
    ): ApiResponse<Any>

    @GET("patient/profile")
    suspend fun me(): ApiResponse<MeResponse>
}