package com.lans.sleep_care.presentation.screen.chat_room

sealed class ChatRoomUIEvent {
    data class MessageChanged(val message: String): ChatRoomUIEvent()
    data object SendButtonClicked: ChatRoomUIEvent()
}