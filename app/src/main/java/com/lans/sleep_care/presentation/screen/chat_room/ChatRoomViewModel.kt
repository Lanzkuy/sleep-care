package com.lans.sleep_care.presentation.screen.chat_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
) : ViewModel() {
    private val _state = mutableStateOf(ChatRoomUIState())
    val state: State<ChatRoomUIState> get() = _state

    fun onEvent(event: ChatRoomUIEvent) {
        when (event) {
            is ChatRoomUIEvent.MessageChanged -> {
                _state.value = _state.value.copy(
                    message = _state.value.message.copy(
                        value = event.message
                    )
                )
            }

            is ChatRoomUIEvent.SendButtonClicked -> {
                sendMessage()
            }
        }
    }

    private fun sendMessage() {

    }
}
