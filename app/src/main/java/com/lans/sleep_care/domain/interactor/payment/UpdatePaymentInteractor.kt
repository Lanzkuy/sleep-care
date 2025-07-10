package com.lans.sleep_care.domain.interactor.payment

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.payment.UpdatePaymentRequest
import com.lans.sleep_care.domain.repository.IPaymentRepository
import com.lans.sleep_care.domain.usecase.payment.UpdatePaymentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdatePaymentInteractor @Inject constructor(
    private val repository: IPaymentRepository
) : UpdatePaymentUseCase, SafeApiCall {
    override suspend fun execute(orderId: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response =
                        repository.updateMidtransPayment(UpdatePaymentRequest(orderId)).message
                    response == "Update midtrans berhasil."
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}