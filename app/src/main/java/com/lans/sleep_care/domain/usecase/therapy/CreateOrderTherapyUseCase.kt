package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface CreateOrderTherapyUseCase {
    suspend fun execute(doctorId: Int): Flow<Resource<Order>>
}