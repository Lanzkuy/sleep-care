package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.request.therapy.OrderTherapyRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatListResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.OrderTherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyScheduleListResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyScheduleResponse

interface ITherapyRepository {
    suspend fun fetchTherapy(): ApiResponse<TherapyResponse>

    suspend fun fetchTherapySchedules(therapyId: Int): ApiResponse<TherapyScheduleListResponse>

    suspend fun fetchOrderTherapyStatus(): ApiResponse<List<OrderTherapyResponse>>

    suspend fun createOrderTherapy(request: OrderTherapyRequest): ApiResponse<OrderTherapyResponse>

    suspend fun fetchChatHistory(): ApiResponse<ChatListResponse>

    suspend fun sendChat(request: ChatRequest): ApiResponse<ChatResponse>
}