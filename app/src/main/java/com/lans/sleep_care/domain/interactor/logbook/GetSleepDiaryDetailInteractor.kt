package com.lans.sleep_care.domain.interactor.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.logbook.SleepDiaryRequest
import com.lans.sleep_care.data.source.network.dto.response.logbook.toDomain
import com.lans.sleep_care.domain.model.logbook.LogbookAnswerList
import com.lans.sleep_care.domain.repository.ILogbookRepository
import com.lans.sleep_care.domain.usecase.logbook.GetSleepDiaryDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSleepDiaryDetailInteractor @Inject constructor(
    private val repository: ILogbookRepository
) : GetSleepDiaryDetailUseCase, SafeApiCall {
    override suspend fun execute(
        sleepDiaryId: Int,
        therapyId: Int
    ): Flow<Resource<LogbookAnswerList>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.fetchSleepDiaryDetail(
                        sleepDiaryId = sleepDiaryId,
                        request = SleepDiaryRequest(
                            therapyId = therapyId
                        )
                    )
                    response.data?.toDomain() ?: throw Exception()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}