package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface GetTransactionStatusUseCase {
    suspend fun execute(orderId: String): Flow<Resource<String>>
}