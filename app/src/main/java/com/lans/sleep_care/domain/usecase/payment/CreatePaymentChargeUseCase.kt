package com.lans.sleep_care.domain.usecase.payment

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.User
import kotlinx.coroutines.flow.Flow

interface CreatePaymentChargeUseCase {
    suspend fun execute(
        orderId: String,
        user: User
    ): Flow<Resource<Pair<String, String>>>
}