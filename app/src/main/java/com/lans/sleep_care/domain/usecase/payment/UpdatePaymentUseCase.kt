package com.lans.sleep_care.domain.usecase.payment

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface UpdatePaymentUseCase {
    suspend fun execute(orderId: String): Flow<Resource<Boolean>>
}