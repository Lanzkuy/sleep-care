package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity
import com.lans.sleep_care.data.source.network.dto.request.chat.ChatBotRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chat.ChatBotResponse
import com.lans.sleep_care.domain.model.ChatBot
import kotlinx.coroutines.flow.Flow

interface IChatBotRepository {
    suspend fun saveChat(email: String, chatBot: ChatBot)

    suspend fun fetchHistory(email: String): Flow<List<ChatBotHistoryEntity>>

    suspend fun fetchAnswer(request: ChatBotRequest): ApiResponse<ChatBotResponse>
}