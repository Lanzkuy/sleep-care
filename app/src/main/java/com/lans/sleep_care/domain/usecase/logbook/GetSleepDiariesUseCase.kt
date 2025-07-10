package com.lans.sleep_care.domain.usecase.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.logbook.SleepDiary
import kotlinx.coroutines.flow.Flow

interface GetSleepDiariesUseCase {
    suspend fun execute(therapyId: Int): Flow<Resource<List<SleepDiary>>>
}