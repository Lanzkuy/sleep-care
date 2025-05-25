package com.lans.sleep_care.presentation.screen.chat_room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.domain.model.validation.InputWrapper
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.therapy.GetChatHistoryUseCase
import com.lans.sleep_care.domain.usecase.therapy.SendChatUseCase
import com.lans.sleep_care.domain.usecase.therapy.UpdateChatReadStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val sendChatUseCase: SendChatUseCase,
    private val updateChatReadStatusUseCase: UpdateChatReadStatusUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ChatRoomUIState())
    val state: State<ChatRoomUIState> get() = _state
    private var pollingJob: Job? = null

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
                val message = _state.value.message.value
                if (message.isBlank()) {
                    return
                }

                _state.value = _state.value.copy(message = InputWrapper())
                sendMessage(message)
            }
        }
    }

    fun startPollingChatHistory(intervalMillis: Long = 5000L) {
        if (pollingJob?.isActive == true) return

        pollingJob = viewModelScope.launch {
            getChatHistory(isInitial = true)
            delay(intervalMillis)

            while (isActive) {
                getChatHistory()
                delay(intervalMillis)
            }
        }
    }

    fun stopPollingChatHistory() {
        pollingJob?.cancel()
    }

    private suspend fun getChatHistory(isInitial: Boolean = false) {
        viewModelScope.launch {
            getChatHistoryUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            chatHistories = response.data.toMutableList()
                        )

                        _state.value.chatHistories
                            .filter { it.readAt == "" }
                            .also { unreadMessages ->
                                if (unreadMessages.isEmpty()) {
                                    _state.value = _state.value.copy(
                                        isHistoryLoading = false
                                    )
                                } else {
                                    unreadMessages.forEach { message ->
                                        updateChatReadStatus(message.id)
                                    }
                                }
                            }
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isHistoryLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        if (isInitial) {
                            _state.value = _state.value.copy(
                                isHistoryLoading = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            sendChatUseCase.execute(message).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        val newMessage = response.data
                        val newHistories = (_state.value.chatHistories + newMessage).toMutableList()
                        _state.value = _state.value.copy(
                            chatHistories = newHistories,
                            isChatSent = true,
                            isSendChatLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isSendChatLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isSendChatLoading = true
                        )
                    }
                }
            }
        }
    }

    private fun updateChatReadStatus(chatId: Int) {
        viewModelScope.launch {
            updateChatReadStatusUseCase.execute(chatId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
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
                }
            }
        }
    }
}
