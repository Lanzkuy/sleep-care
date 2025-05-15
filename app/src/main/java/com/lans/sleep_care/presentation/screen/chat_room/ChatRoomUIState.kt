package com.lans.sleep_care.presentation.screen.chat_room

import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.domain.model.Chat

data class ChatRoomUIState(
    val message: InputWrapper = InputWrapper(),
    val isHistoryLoading: Boolean = false,
    val isSendChatLoading: Boolean = false,
    var error: String = "",
    val chatHistories: List<Chat> = emptyList(),
    val isChatSent: Boolean = false,
)