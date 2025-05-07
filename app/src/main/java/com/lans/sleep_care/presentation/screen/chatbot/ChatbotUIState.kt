package com.lans.sleep_care.presentation.screen.chatbot

import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.domain.model.Chat

data class ChatbotUIState(
    val message: InputWrapper = InputWrapper(),
    var email: String = "",
    var name: String = "",
    val isHistoryLoading: Boolean = false,
    val isBotLoading: Boolean = false,
    var error: String = "",
    val chatHistory: MutableList<Chat> = mutableListOf()
)