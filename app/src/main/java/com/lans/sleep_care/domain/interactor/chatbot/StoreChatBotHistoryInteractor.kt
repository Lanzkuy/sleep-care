package com.lans.sleep_care.domain.interactor.chatbot

import com.lans.sleep_care.domain.model.Chat
import com.lans.sleep_care.domain.repository.IChatBotRepository
import com.lans.sleep_care.domain.usecase.chatbot.StoreChatBotHistoryUseCase
import javax.inject.Inject

class StoreChatBotHistoryInteractor @Inject constructor(
    private val repository: IChatBotRepository
): StoreChatBotHistoryUseCase {
    override suspend fun execute(email: String, chat: Chat) {
        repository.storeMessage(email, chat)
    }
}