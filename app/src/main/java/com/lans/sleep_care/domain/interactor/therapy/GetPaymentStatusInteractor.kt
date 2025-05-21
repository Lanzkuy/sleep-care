package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.GetPaymentStatusUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPaymentStatusInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : GetPaymentStatusUseCase {
    override suspend fun execute(orderId: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading)
            val response = repository.fetchPaymentStatus(orderId)
            if(response.statusCode == "200") {
                emit(Resource.Success(response.transactionStatus))
            }
        }.catch { e ->
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }
}