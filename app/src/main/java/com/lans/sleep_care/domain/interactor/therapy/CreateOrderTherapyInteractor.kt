package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.therapy.OrderTherapyRequest
import com.lans.sleep_care.data.source.network.dto.response.therapy.toDomain
import com.lans.sleep_care.domain.model.therapy.Order
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.CreateOrderTherapyUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateOrderTherapyInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : CreateOrderTherapyUseCase, SafeApiCall {
    override suspend fun execute(doctorId: Int): Flow<Resource<Order>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.createOrderTherapy(OrderTherapyRequest(doctorId)).data
                    response?.toDomain() ?: throw Exception()
                }
            )
        }
    }
}