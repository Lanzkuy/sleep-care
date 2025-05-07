package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.local.dao.ChatBotHistoryDao
import com.lans.sleep_care.data.source.local.entity.ChatBotHistoryEntity
import com.lans.sleep_care.data.source.network.api.ChatBotApi
import com.lans.sleep_care.data.source.network.dto.request.ChatBotRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.ChatBotResponse
import com.lans.sleep_care.domain.model.Chat
import com.lans.sleep_care.domain.model.toEntity
import com.lans.sleep_care.domain.repository.IChatBotRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatBotRepository @Inject constructor(
    private val api: ChatBotApi,
    private val dao: ChatBotHistoryDao
) : IChatBotRepository {
    override suspend fun storeMessage(email: String, chat: Chat) {
        dao.create(chat.toEntity().copy(email = email))
    }

    override suspend fun getChatHistory(email: String): Flow<List<ChatBotHistoryEntity>> {
        return dao.getAllHistories(email)
    }

    override suspend fun getAnswer(request: ChatBotRequest): ApiResponse<ChatBotResponse> {
        return api.chat(request)
    }
}