package com.lans.sleep_care.domain.usecase.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import kotlinx.coroutines.flow.Flow

interface UpdateLogbookAnswerUseCase {
    suspend fun execute(
        therapyId: Int,
        recordId: Int,
        recordType: String,
        questionAnswers: List<LogbookQuestionAnswer>
    ): Flow<Resource<Boolean>>
}