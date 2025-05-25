package com.lans.sleep_care.presentation.screen.chatbot

import com.lans.sleep_care.domain.model.validation.InputWrapper
import com.lans.sleep_care.domain.model.chatbot.ChatBot

data class ChatbotUIState(
    val message: InputWrapper = InputWrapper(),
    var email: String = "",
    var name: String = "",
    val isHistoryLoading: Boolean = false,
    val isBotLoading: Boolean = false,
    var error: String = "",
    val chatBotHistory: MutableList<ChatBot> = mutableListOf()
)