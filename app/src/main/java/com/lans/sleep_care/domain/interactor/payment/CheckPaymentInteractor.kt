package com.lans.sleep_care.domain.interactor.payment

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.domain.repository.IPaymentRepository
import com.lans.sleep_care.domain.usecase.payment.CheckPaymentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CheckPaymentInteractor @Inject constructor(
    private val repository: IPaymentRepository
) : CheckPaymentUseCase, SafeApiCall {
    override suspend fun execute(orderId: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.checkMidtransPayment(orderId)
                    response.transactionStatus
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}