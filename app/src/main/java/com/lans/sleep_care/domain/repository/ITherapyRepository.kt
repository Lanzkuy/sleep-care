package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.request.therapy.OrderTherapyRequest
import com.lans.sleep_care.data.source.network.dto.request.therapy.RatingTherapyRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chatbot.ChatListResponse
import com.lans.sleep_care.data.source.network.dto.response.chatbot.ChatResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.OrderTherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.RatingTherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyListResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.TherapyScheduleListResponse

interface ITherapyRepository {
    suspend fun fetchActiveTherapy(): ApiResponse<TherapyResponse>

    suspend fun fetchCompletedTherapy(): ApiResponse<TherapyListResponse>

    suspend fun fetchTherapySchedules(therapyId: Int): ApiResponse<TherapyScheduleListResponse>

    suspend fun fetchOrderTherapyStatus(): ApiResponse<OrderTherapyResponse>

    suspend fun createOrderTherapy(request: OrderTherapyRequest): ApiResponse<OrderTherapyResponse>

    suspend fun fetchChatHistory(): ApiResponse<ChatListResponse>

    suspend fun sendChat(request: ChatRequest): ApiResponse<ChatResponse>

    suspend fun updateChat(chatId: Int): ApiResponse<Any>

    suspend fun createRating(request: RatingTherapyRequest): ApiResponse<RatingTherapyResponse>
}