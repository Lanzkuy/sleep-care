package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatListResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatResponse
import com.lans.sleep_care.data.source.network.dto.response.ScheduleListResponse
import com.lans.sleep_care.data.source.network.dto.response.TherapyResponse
import com.lans.sleep_care.domain.repository.ITherapyRepository
import javax.inject.Inject

class TherapyRepository @Inject constructor(
    private val api: SleepCareApi
) : ITherapyRepository {
    override suspend fun fetchTherapy(): ApiResponse<List<TherapyResponse>> {
        return api.getActiveTherapy()
    }

    override suspend fun fetchSchedules(): ApiResponse<ScheduleListResponse> {
        return api.getSchedules()
    }

    override suspend fun fetchChatHistory(): ApiResponse<ChatListResponse> {
        return api.getChatHistory()
    }

    override suspend fun sendChat(request: ChatRequest): ApiResponse<ChatResponse> {
        return api.sendChat(request)
    }
}