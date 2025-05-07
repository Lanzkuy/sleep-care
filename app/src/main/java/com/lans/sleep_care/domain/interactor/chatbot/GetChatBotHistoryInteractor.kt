package com.lans.sleep_care.domain.interactor.chatbot

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.local.entity.toDomain
import com.lans.sleep_care.domain.model.Chat
import com.lans.sleep_care.domain.repository.IChatBotRepository
import com.lans.sleep_care.domain.usecase.chatbot.GetChatBotHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetChatBotHistoryInteractor @Inject constructor(
    private val repository: IChatBotRepository
) : GetChatBotHistoryUseCase {
    override suspend fun execute(email: String): Flow<Resource<List<Chat>>> {
        return flow {
            emit(Resource.Loading)
            repository.getChatHistory(email)
                .map { list ->
                    val chats = list.map { it.toDomain() }
                    Resource.Success(chats)
                }.catch { e ->
                    emit(Resource.Error("An unknown error occurred: ${e.localizedMessage}"))
                }
                .collect { (emit(it)) }
        }.flowOn(Dispatchers.IO)
    }
}