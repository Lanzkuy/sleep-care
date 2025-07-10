package com.lans.sleep_care.presentation.screen.chatbot

sealed class ChatbotUIEvent {
    data class MessageChanged(val message: String): ChatbotUIEvent()
    data object SendButtonClicked: ChatbotUIEvent()
}