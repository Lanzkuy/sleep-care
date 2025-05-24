package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.Therapy
import kotlinx.coroutines.flow.Flow

interface GetActiveTherapyUseCase {
    suspend fun execute(): Flow<Resource<Therapy?>>
}