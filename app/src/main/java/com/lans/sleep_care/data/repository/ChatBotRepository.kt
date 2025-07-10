package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.local.dao.ChatBotHistoryDao
import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity
import com.lans.sleep_care.data.source.network.api.ChatBotApi
import com.lans.sleep_care.data.source.network.dto.request.chatbot.ChatBotRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.chatbot.ChatBotResponse
import com.lans.sleep_care.domain.model.chatbot.ChatBot
import com.lans.sleep_care.domain.model.chatbot.toEntity
import com.lans.sleep_care.domain.repository.IChatBotRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatBotRepository @Inject constructor(
    private val api: ChatBotApi,
    private val dao: ChatBotHistoryDao
) : IChatBotRepository {
    override suspend fun fetchHistory(email: String): Flow<List<ChatBotHistoryEntity>> {
        return dao.getAllHistories(email)
    }

    override suspend fun fetchAnswer(request: ChatBotRequest): ApiResponse<ChatBotResponse> {
        return api.chat(request)
    }

    override suspend fun saveChat(email: String, chatBot: ChatBot) {
        dao.create(chatBot.toEntity().copy(email = email))
    }
}