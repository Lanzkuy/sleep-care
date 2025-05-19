package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatListResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatResponse
import com.lans.sleep_care.data.source.network.dto.response.ScheduleListResponse
import com.lans.sleep_care.data.source.network.dto.response.TherapyResponse

interface ITherapyRepository {
    suspend fun fetchTherapy(): ApiResponse<List<TherapyResponse>>
    suspend fun fetchSchedules(): ApiResponse<ScheduleListResponse>
    suspend fun fetchChatHistory(): ApiResponse<ChatListResponse>
    suspend fun sendChat(request: ChatRequest): ApiResponse<ChatResponse>
}