package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity
import com.lans.sleep_care.data.source.network.dto.request.ChatBotRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatBotResponse
import com.lans.sleep_care.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface IChatBotRepository {
    suspend fun storeMessage(email: String, chat: Chat)

    suspend fun getChatHistory(email: String): Flow<List<ChatBotHistoryEntity>>

    suspend fun getAnswer(request: ChatBotRequest): ApiResponse<ChatBotResponse>
}