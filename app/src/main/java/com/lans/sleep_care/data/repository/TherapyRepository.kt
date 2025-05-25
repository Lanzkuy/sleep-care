package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.request.therapy.OrderTherapyRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatListResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.OrderTherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyScheduleListResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyScheduleResponse
import com.lans.sleep_care.domain.repository.ITherapyRepository
import javax.inject.Inject

class TherapyRepository @Inject constructor(
    private val api: SleepCareApi
) : ITherapyRepository {
    override suspend fun fetchTherapy(): ApiResponse<TherapyResponse> {
        return api.getActiveTherapy()
    }

    override suspend fun fetchTherapySchedules(therapyId: Int): ApiResponse<TherapyScheduleListResponse> {
        return api.getSchedules(therapyId)
    }

    override suspend fun fetchOrderTherapyStatus(): ApiResponse<OrderTherapyResponse> {
       return api.getOrderStatus()
    }

    override suspend fun createOrderTherapy(request: OrderTherapyRequest): ApiResponse<OrderTherapyResponse> {
        return api.createOrder(request)
    }

    override suspend fun fetchChatHistory(): ApiResponse<ChatListResponse> {
        return api.getChatHistory()
    }

    override suspend fun sendChat(request: ChatRequest): ApiResponse<ChatResponse> {
        return api.sendChat(request)
    }

    override suspend fun updateChat(chatId: Int): ApiResponse<Any> {
        return api.updateChat(chatId)
    }
}