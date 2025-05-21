package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.auth.PasswordForgotRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.LoginRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.OtpRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.PasswordResetRequest
import com.lans.sleep_care.data.source.network.dto.request.auth.OtpVerifyRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.auth.LoginResponse
import com.lans.sleep_care.data.source.network.dto.response.auth.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun authState(): Flow<Boolean>

    suspend fun saveToken(accessToken: String)

    suspend fun deleteToken()

    suspend fun fetchLogin(
        request: LoginRequest
    ): ApiResponse<LoginResponse>

    suspend fun fetchRegister(
        request: RegisterRequest
    ): ApiResponse<RegisterResponse>

    suspend fun forgotPassword(
        request: PasswordForgotRequest
    ): ApiResponse<Any>

    suspend fun resetPassword(
        request: PasswordResetRequest
    ): ApiResponse<Any>

    suspend fun sendOtp(
        request: OtpRequest
    ): ApiResponse<Any>

    suspend fun verifyOtp(
        request: OtpVerifyRequest
    ): ApiResponse<Any>
}