package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.UpdateChatReadStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateChatReadStatusInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : UpdateChatReadStatusUseCase, SafeApiCall {
    override suspend fun execute(chatId: Int): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.updateChat(chatId).message
                    response == "Berhasil mengubah data profile."
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}