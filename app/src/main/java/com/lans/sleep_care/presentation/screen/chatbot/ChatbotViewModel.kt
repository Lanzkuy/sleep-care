package com.lans.sleep_care.presentation.screen.chatbot

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.Chat
import com.lans.sleep_care.domain.usecase.chatbot.GetChatBotAnswerUseCase
import com.lans.sleep_care.domain.usecase.chatbot.GetChatBotHistoryUseCase
import com.lans.sleep_care.domain.usecase.chatbot.StoreChatBotHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatbotViewModel @Inject constructor(
    private val getChatBotAnswerUseCase: GetChatBotAnswerUseCase,
    private val storeChatBotHistoryUseCase: StoreChatBotHistoryUseCase,
    private val getChatBotHistoryUseCase: GetChatBotHistoryUseCase
) : ViewModel() {
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
                val message = _state.value.message.value
                storeChat(sender = _state.value.name, message = message)
                _state.value = _state.value.copy(message = InputWrapper())
                getAnswer(message)
            }
        }
    }

    private fun storeChat(sender: String, message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            storeChatBotHistoryUseCase.execute(
                email = _state.value.email,
                chat = Chat(
                    sender = sender,
                    message = message,
                )
            )
        }
    }

    private fun getAnswer(message: String) {
        viewModelScope.launch {
            getChatBotAnswerUseCase.execute(message).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        storeChat(sender = "bot", message = response.data)
                        _state.value = _state.value.copy(
                            message = InputWrapper(),
                            isBotLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isBotLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isBotLoading = true
                        )
                    }

                    else -> Unit
                }
            }
        }
    }

    fun getChatHistory() {
        viewModelScope.launch {
            getChatBotHistoryUseCase.execute(_state.value.email).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            chatHistory = response.data.toMutableList(),
                            isHistoryLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isHistoryLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isHistoryLoading = true
                        )
                    }

                    else -> Unit
                }
            }
        }
    }
}