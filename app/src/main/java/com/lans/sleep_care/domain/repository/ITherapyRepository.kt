package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.ScheduleListResponse
import com.lans.sleep_care.data.source.network.dto.response.ScheduleResponse

interface ITherapyRepository {
    suspend fun fetchSchedules(): ApiResponse<ScheduleListResponse>
}