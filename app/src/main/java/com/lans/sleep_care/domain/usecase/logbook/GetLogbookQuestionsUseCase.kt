package com.lans.sleep_care.domain.usecase.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import kotlinx.coroutines.flow.Flow

interface GetLogbookQuestionsUseCase {
    suspend fun execute(recordType: String): Flow<Resource<List<LogbookQuestion>>>
}