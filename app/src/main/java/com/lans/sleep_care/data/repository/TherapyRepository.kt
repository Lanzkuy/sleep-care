package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.ScheduleListResponse
import com.lans.sleep_care.data.source.network.dto.response.ScheduleResponse
import com.lans.sleep_care.domain.repository.ITherapyRepository
import javax.inject.Inject

class TherapyRepository @Inject constructor(
    private val api: SleepCareApi
) : ITherapyRepository {
    override suspend fun fetchSchedules(): ApiResponse<ScheduleListResponse> {
        return api.getSchedules()
    }
}