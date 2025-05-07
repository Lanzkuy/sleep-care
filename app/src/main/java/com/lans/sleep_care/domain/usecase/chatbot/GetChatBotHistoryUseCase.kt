package com.lans.sleep_care.domain.usecase.chatbot

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface GetChatBotHistoryUseCase {
    suspend fun execute(email: String): Flow<Resource<List<Chat>>>
}