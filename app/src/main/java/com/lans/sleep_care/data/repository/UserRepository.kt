package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.ChangePasswordRequest
import com.lans.sleep_care.data.source.network.dto.request.UpdateProfileRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.MeResponse
import com.lans.sleep_care.domain.repository.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: SleepCareApi
) : IUserRepository {
    override suspend fun fetchProfile(): ApiResponse<MeResponse> {
        return api.me()
    }

    override suspend fun updateProfile(request: UpdateProfileRequest): ApiResponse<Any> {
        return api.updateProfile(request)
    }

    override suspend fun changePassword(request: ChangePasswordRequest): ApiResponse<Any> {
        return api.changePassword(request)
    }
}