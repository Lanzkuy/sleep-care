package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.ForgotPasswordRequest
import com.lans.sleep_care.data.source.network.dto.request.LoginRequest
import com.lans.sleep_care.data.source.network.dto.request.OtpRequest
import com.lans.sleep_care.data.source.network.dto.request.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.request.ResetPasswordRequest
import com.lans.sleep_care.data.source.network.dto.request.VerifyOtpRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.LoginResponse
import com.lans.sleep_care.data.source.network.dto.response.RegisterResponse
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
        request: ForgotPasswordRequest
    ): ApiResponse<Any>

    suspend fun resetPassword(
        request: ResetPasswordRequest
    ): ApiResponse<Any>

    suspend fun sendOtp(
        request: OtpRequest
    ): ApiResponse<Any>

    suspend fun verifyOtp(
        request: VerifyOtpRequest
    ): ApiResponse<Any>
}