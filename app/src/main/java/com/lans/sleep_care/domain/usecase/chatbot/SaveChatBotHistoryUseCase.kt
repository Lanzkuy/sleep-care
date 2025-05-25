package com.lans.sleep_care.domain.usecase.chatbot

import com.lans.sleep_care.domain.model.chatbot.ChatBot

interface SaveChatBotHistoryUseCase {
    suspend fun execute(email: String, chatBot: ChatBot)
}