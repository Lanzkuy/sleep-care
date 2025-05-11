package com.lans.sleep_care.domain.usecase.chatbot

import com.lans.sleep_care.domain.model.Chat

interface SaveChatBotHistoryUseCase {
    suspend fun execute(email: String, chat: Chat)
}