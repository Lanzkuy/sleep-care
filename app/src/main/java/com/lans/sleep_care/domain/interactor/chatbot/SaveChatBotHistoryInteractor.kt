package com.lans.sleep_care.domain.interactor.chatbot

import com.lans.sleep_care.domain.model.Chat
import com.lans.sleep_care.domain.repository.IChatBotRepository
import com.lans.sleep_care.domain.usecase.chatbot.SaveChatBotHistoryUseCase
import javax.inject.Inject

class SaveChatBotHistoryInteractor @Inject constructor(
    private val repository: IChatBotRepository
): SaveChatBotHistoryUseCase {
    override suspend fun execute(email: String, chat: Chat) {
        repository.saveChat(email, chat)
    }
}