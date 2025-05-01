package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.LoginRequest
import com.lans.sleep_care.data.source.network.dto.request.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.LoginResponse
import com.lans.sleep_care.data.source.network.dto.response.RegisterResponse

interface IAuthRepository {
    suspend fun login(
        request: LoginRequest
    ): ApiResponse<LoginResponse>

    suspend fun register(
        request: RegisterRequest
    ): ApiResponse<RegisterResponse>
}