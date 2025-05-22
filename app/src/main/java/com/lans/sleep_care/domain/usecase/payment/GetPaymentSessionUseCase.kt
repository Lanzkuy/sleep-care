package com.lans.sleep_care.domain.usecase.payment

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface GetPaymentSessionUseCase {
    suspend fun execute(): Flow<Resource<Triple<String, String, Int>>>
}