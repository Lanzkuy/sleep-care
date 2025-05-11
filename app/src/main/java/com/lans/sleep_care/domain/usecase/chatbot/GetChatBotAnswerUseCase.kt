package com.lans.sleep_care.domain.usecase.chatbot

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface GetChatBotAnswerUseCase {
    suspend fun execute(message: String): Flow<Resource<String>>
}