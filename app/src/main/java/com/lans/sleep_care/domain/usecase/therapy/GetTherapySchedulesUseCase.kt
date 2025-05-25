package com.lans.sleep_care.domain.usecase.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.therapy.TherapySchedule
import kotlinx.coroutines.flow.Flow

interface GetTherapySchedulesUseCase {
    suspend fun execute(therapyId: Int): Flow<Resource<List<TherapySchedule>>>
}