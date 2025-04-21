package com.lans.sleep_care.presentation.screen.chat_room

import com.lans.instagram_clone.domain.model.InputWrapper

data class ChatRoomUIState(
    var message: InputWrapper = InputWrapper(),
    var isLoading: Boolean = false,
    var error: String = "",
    var chatHistories: String? = null
)