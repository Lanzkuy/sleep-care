package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.GetTransactionStatusUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTransactionStatusInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : GetTransactionStatusUseCase {
    override suspend fun execute(orderId: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading)
            val response = repository.fetchTransactionStatus(orderId)
            if(response.statusCode == "200") {
                emit(Resource.Success(response.transactionStatus))
            }
        }.catch { e ->
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }
}