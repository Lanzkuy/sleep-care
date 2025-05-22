package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface GetOrderTherapyStatusUseCase {
    suspend fun execute(): Flow<Resource<Order>>
}