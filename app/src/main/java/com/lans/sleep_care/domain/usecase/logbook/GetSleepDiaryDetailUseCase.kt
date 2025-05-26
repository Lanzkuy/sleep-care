package com.lans.sleep_care.domain.usecase.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.logbook.SleepDiaryDetail
import kotlinx.coroutines.flow.Flow

interface GetSleepDiaryDetailUseCase {
    suspend fun execute(sleepDiaryId: Int, therapyId: Int): Flow<Resource<SleepDiaryDetail>>
}