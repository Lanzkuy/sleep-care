package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.user.PasswordChangeRequest
import com.lans.sleep_care.data.source.network.dto.request.user.ProfileUpdateRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.user.UserResponse

interface IUserRepository {
    suspend fun fetchProfile(): ApiResponse<UserResponse>

    suspend fun updateProfile(
        request: ProfileUpdateRequest
    ): ApiResponse<Any>

    suspend fun changePassword(
        request: PasswordChangeRequest
    ): ApiResponse<Any>
}