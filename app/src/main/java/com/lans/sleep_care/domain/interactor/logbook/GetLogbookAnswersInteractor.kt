package com.lans.sleep_care.domain.interactor.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.logbook.LogbookAnswerRequest
import com.lans.sleep_care.data.source.network.dto.response.logbook.toDomain
import com.lans.sleep_care.domain.model.logbook.LogbookAnswerList
import com.lans.sleep_care.domain.repository.ILogbookRepository
import com.lans.sleep_care.domain.usecase.logbook.GetLogbookAnswersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLogbookAnswersInteractor @Inject constructor(
    private val repository: ILogbookRepository
) : GetLogbookAnswersUseCase, SafeApiCall {
    override suspend fun execute(
        recordType: String,
        therapyId: Int,
        week: Int
    ): Flow<Resource<LogbookAnswerList>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.fetchAnswers(
                        LogbookAnswerRequest(
                            recordType = recordType,
                            therapyId = therapyId,
                            week = week
                        )
                    )
                    response.data?.toDomain() ?: throw Exception()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}