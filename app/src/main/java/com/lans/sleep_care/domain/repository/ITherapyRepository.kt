package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatListResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyScheduleListResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.PaymentStatusResponse

interface ITherapyRepository {
    suspend fun fetchTherapy(): ApiResponse<List<TherapyResponse>>
    suspend fun fetchTherapySchedules(): ApiResponse<TherapyScheduleListResponse>
    suspend fun fetchChatHistory(): ApiResponse<ChatListResponse>
    suspend fun fetchPaymentStatus(orderId: String): PaymentStatusResponse
    suspend fun sendChat(request: ChatRequest): ApiResponse<ChatResponse>
}