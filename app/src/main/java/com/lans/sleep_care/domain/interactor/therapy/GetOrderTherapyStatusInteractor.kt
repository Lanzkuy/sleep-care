package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.response.therapy.toDomain
import com.lans.sleep_care.domain.model.Order
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.GetOrderTherapyStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOrderTherapyStatusInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : GetOrderTherapyStatusUseCase, SafeApiCall {
    override suspend fun execute(): Flow<Resource<Order>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = repository.fetchOrderTherapyStatus().data
                response?.toDomain() ?: throw Exception()
            })
        }.flowOn(Dispatchers.IO)
    }
}