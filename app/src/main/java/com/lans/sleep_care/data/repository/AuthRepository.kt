package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.local.DataStoreManager
import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.ForgotPasswordRequest
import com.lans.sleep_care.data.source.network.dto.request.LoginRequest
import com.lans.sleep_care.data.source.network.dto.request.OtpRequest
import com.lans.sleep_care.data.source.network.dto.request.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.request.ResetPasswordRequest
import com.lans.sleep_care.data.source.network.dto.request.VerifyOtpRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.LoginResponse
import com.lans.sleep_care.data.source.network.dto.response.RegisterResponse
import com.lans.sleep_care.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: SleepCareApi,
    private val dataStoreManager: DataStoreManager
) : IAuthRepository {
    override suspend fun authState(): Flow<Boolean> {
        return dataStoreManager.getAccessToken().map { it.isNotEmpty() }
    }

    override suspend fun saveToken(accessToken: String) {
        dataStoreManager.storeData(key = DataStoreManager.ACCESS_TOKEN, value = accessToken)
    }

    override suspend fun deleteToken() {
        dataStoreManager.clear()
    }

    override suspend fun fetchLogin(request: LoginRequest): ApiResponse<LoginResponse> {
        return api.login(request)
    }

    override suspend fun fetchRegister(request: RegisterRequest): ApiResponse<RegisterResponse> {
        return api.register(request)
    }

    override suspend fun forgotPassword(request: ForgotPasswordRequest): ApiResponse<Any> {
        return api.forgotPassword(request)
    }

    override suspend fun resetPassword(request: ResetPasswordRequest): ApiResponse<Any> {
        return api.resetPassword(request)
    }

    override suspend fun sendOtp(request: OtpRequest): ApiResponse<Any> {
        return api.requestOtp(request)
    }

    override suspend fun verifyOtp(request: VerifyOtpRequest): ApiResponse<Any> {
        return api.verifyOtp(request)
    }
}