package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.ChangePasswordRequest
import com.lans.sleep_care.data.source.network.dto.request.ForgotPasswordRequest
import com.lans.sleep_care.data.source.network.dto.request.UpdateProfileRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.MeResponse

interface IUserRepository {
    suspend fun fetchProfile(): ApiResponse<MeResponse>

    suspend fun updateProfile(
        request: UpdateProfileRequest
    ): ApiResponse<Any>

    suspend fun changePassword(
        request: ChangePasswordRequest
    ): ApiResponse<Any>
}