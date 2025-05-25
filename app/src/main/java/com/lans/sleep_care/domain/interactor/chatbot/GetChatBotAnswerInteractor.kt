package com.lans.sleep_care.domain.interactor.chatbot

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.chatbot.ChatBotRequest
import com.lans.sleep_care.domain.repository.IChatBotRepository
import com.lans.sleep_care.domain.usecase.chatbot.GetChatBotAnswerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetChatBotAnswerInteractor @Inject constructor(
    private val repository: IChatBotRepository
) : GetChatBotAnswerUseCase, SafeApiCall {
    override suspend fun execute(message: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                repository.fetchAnswer(
                    ChatBotRequest(message)
                ).data?.response ?: throw Exception()
            })
        }.flowOn(Dispatchers.IO)
    }
}