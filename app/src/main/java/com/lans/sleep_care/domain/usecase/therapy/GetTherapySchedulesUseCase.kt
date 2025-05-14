package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.TherapySchedule
import kotlinx.coroutines.flow.Flow

interface GetTherapySchedulesUseCase {
    suspend fun execute(): Flow<Resource<List<TherapySchedule>>>
}