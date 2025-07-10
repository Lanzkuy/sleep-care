package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity
import com.lans.sleep_care.data.source.network.dto.request.chatbot.ChatBotRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chatbot.ChatBotResponse
import com.lans.sleep_care.domain.model.chatbot.ChatBot
import kotlinx.coroutines.flow.Flow

interface IChatBotRepository {
    suspend fun fetchHistory(email: String): Flow<List<ChatBotHistoryEntity>>

    suspend fun fetchAnswer(request: ChatBotRequest): ApiResponse<ChatBotResponse>

    suspend fun saveChat(email: String, chatBot: ChatBot)
}