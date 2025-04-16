package com.lans.sleep_care.presentation.screen.chatbot

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatbotViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(ChatbotUIState())
    val state: State<ChatbotUIState> get() = _state

    fun onEvent(event: ChatbotUIEvent) {
        when (event) {
            is ChatbotUIEvent.MessageChanged -> {
                _state.value = _state.value.copy(
                    message = _state.value.message.copy(
                        value = event.message
                    )
                )
            }

            is ChatbotUIEvent.SendButtonClicked -> {
                sendMessage()
            }
        }
    }

    private fun sendMessage() {

    }
}