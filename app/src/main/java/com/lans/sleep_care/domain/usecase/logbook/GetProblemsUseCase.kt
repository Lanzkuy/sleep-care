package com.lans.sleep_care.domain.usecase.logbook

import com.lans.sleep_care.data.Resource
import kotlinx.coroutines.flow.Flow

interface GetProblemsUseCase {
    suspend fun execute(): Flow<Resource<List<String>>>
}