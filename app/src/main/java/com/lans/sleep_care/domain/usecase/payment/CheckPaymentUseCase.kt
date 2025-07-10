package com.lans.sleep_care.domain.usecase.payment

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface CheckPaymentUseCase {
    suspend fun execute(orderId: String): Flow<Resource<String>>
}