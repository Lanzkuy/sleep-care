package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.response.toDomain
import com.lans.sleep_care.domain.model.Chat
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.GetChatHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetChatHistoryInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : GetChatHistoryUseCase, SafeApiCall {
    override suspend fun execute(): Flow<Resource<List<Chat>>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.fetchChatHistory().data
                    response?.chats?.map { it.toDomain() } ?: throw Exception()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}