package com.lans.sleep_care.domain.interactor.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookQuestionRequest
import com.lans.sleep_care.data.source.network.dto.response.logbook.toDomain
import com.lans.sleep_care.domain.model.logbook.LogbookQuestion
import com.lans.sleep_care.domain.repository.ILogbookRepository
import com.lans.sleep_care.domain.usecase.logbook.GetLogbookQuestionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLogbookQuestionsInteractor @Inject constructor(
    private val repository: ILogbookRepository
) : GetLogbookQuestionsUseCase, SafeApiCall {
    override suspend fun execute(recordType: String): Flow<Resource<List<LogbookQuestion>>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.fetchQuestions(LogbookQuestionRequest(recordType))
                    response.data?.questions?.map { it.toDomain() } ?: throw Exception()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}