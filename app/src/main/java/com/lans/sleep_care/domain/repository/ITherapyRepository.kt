package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatListResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatResponse
import com.lans.sleep_care.data.source.network.dto.response.ScheduleListResponse

interface ITherapyRepository {
    suspend fun fetchSchedules(): ApiResponse<ScheduleListResponse>
    suspend fun fetchChatHistory(): ApiResponse<ChatListResponse>
    suspend fun sendChat(request: ChatRequest): ApiResponse<ChatResponse>
}