package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.LoginRequest
import com.lans.sleep_care.data.source.network.dto.request.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.LoginResponse
import com.lans.sleep_care.data.source.network.dto.response.RegisterResponse
import com.lans.sleep_care.domain.repository.IAuthRepository
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: SleepCareApi
): IAuthRepository {
    override suspend fun login(request: LoginRequest): ApiResponse<LoginResponse> {
        return api.login(request)
    }

    override suspend fun register(request: RegisterRequest): ApiResponse<RegisterResponse> {
        return api.register(request)
    }
}