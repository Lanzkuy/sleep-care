package com.lans.sleep_care.domain.interactor.payment

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.repository.IPaymentRepository
import com.lans.sleep_care.domain.usecase.payment.GetPaymentSessionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPaymentSessionInteractor @Inject constructor(
    private val repository: IPaymentRepository
) : GetPaymentSessionUseCase {
    override suspend fun execute(): Flow<Resource<Triple<String, String, Int>>> {
        return flow {
            emit(Resource.Loading)
            repository.getPaymentSession().collect { session ->
                emit(Resource.Success(session))
            }
        }.flowOn(Dispatchers.IO)
    }
}