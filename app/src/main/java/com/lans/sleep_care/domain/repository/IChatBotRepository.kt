package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity
import com.lans.sleep_care.data.source.network.dto.request.chatbot.ChatBotRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatBotResponse
import com.lans.sleep_care.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface IChatBotRepository {
    suspend fun saveChat(email: String, chat: Chat)

    suspend fun fetchHistory(email: String): Flow<List<ChatBotHistoryEntity>>

    suspend fun fetchAnswer(request: ChatBotRequest): ApiResponse<ChatBotResponse>
}