package com.lans.sleep_care.domain.usecase.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.logbook.LogbookAnswerList
import kotlinx.coroutines.flow.Flow

interface GetLogbookAnswersUseCase {
    suspend fun execute(
        recordType: String,
        therapyId: Int,
        week: Int
    ): Flow<Resource<LogbookAnswerList>>
}