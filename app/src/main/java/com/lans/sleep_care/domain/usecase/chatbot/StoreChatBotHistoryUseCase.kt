package com.lans.sleep_care.domain.usecase.chatbot

import com.lans.sleep_care.domain.model.Chat

interface StoreChatBotHistoryUseCase {
    suspend fun execute(email: String, chat: Chat)
}