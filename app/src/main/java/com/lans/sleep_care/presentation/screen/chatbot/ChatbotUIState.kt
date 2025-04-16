package com.lans.sleep_care.presentation.screen.chatbot

import com.lans.instagram_clone.domain.model.InputWrapper

data class ChatbotUIState(
    var message: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    var error: String = "",
    var chatHistories: String? = null
)