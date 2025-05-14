package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.user.PasswordChangeRequest
import com.lans.sleep_care.data.source.network.dto.request.user.ProfileUpdateRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.UserResponse
import com.lans.sleep_care.domain.repository.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: SleepCareApi
) : IUserRepository {
    override suspend fun fetchProfile(): ApiResponse<UserResponse> {
        return api.me()
    }

    override suspend fun updateProfile(request: ProfileUpdateRequest): ApiResponse<Any> {
        return api.updateProfile(request)
    }

    override suspend fun changePassword(request: PasswordChangeRequest): ApiResponse<Any> {
        return api.changePassword(request)
    }
}