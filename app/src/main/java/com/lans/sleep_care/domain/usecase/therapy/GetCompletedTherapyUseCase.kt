package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.therapy.Therapy
import kotlinx.coroutines.flow.Flow

interface GetCompletedTherapyUseCase {
    suspend fun execute(): Flow<Resource<List<Therapy>>>
}