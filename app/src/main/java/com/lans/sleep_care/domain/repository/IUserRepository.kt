package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.MeResponse

interface IUserRepository {
    suspend fun me(): ApiResponse<MeResponse>
}