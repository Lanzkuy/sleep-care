package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatListResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyScheduleListResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.PaymentStatusResponse
import com.lans.sleep_care.domain.repository.ITherapyRepository
import javax.inject.Inject

class TherapyRepository @Inject constructor(
    private val api: SleepCareApi
) : ITherapyRepository {
    override suspend fun fetchTherapy(): ApiResponse<List<TherapyResponse>> {
        return api.getActiveTherapy()
    }

    override suspend fun fetchTherapySchedules(): ApiResponse<TherapyScheduleListResponse> {
        return api.getSchedules()
    }

    override suspend fun fetchChatHistory(): ApiResponse<ChatListResponse> {
        return api.getChatHistory()
    }

    override suspend fun fetchPaymentStatus(orderId: String): PaymentStatusResponse {
        return api.getMidtransStatus(orderId)
    }

    override suspend fun sendChat(request: ChatRequest): ApiResponse<ChatResponse> {
        return api.sendChat(request)
    }
}