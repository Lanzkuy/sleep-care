package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.therapy.ChatRequest
import com.lans.sleep_care.data.source.network.dto.response.toDomain
import com.lans.sleep_care.domain.model.Chat
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.SendChatUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SendChatInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : SendChatUseCase, SafeApiCall {
    override suspend fun execute(message: String): Flow<Resource<Chat>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.sendChat(ChatRequest(message)).data
                    response?.toDomain() ?: throw Exception()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}