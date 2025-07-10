package com.lans.sleep_care.domain.interactor.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.logbook.SleepDiaryRequest
import com.lans.sleep_care.data.source.network.dto.response.logbook.toDomain
import com.lans.sleep_care.domain.model.logbook.SleepDiary
import com.lans.sleep_care.domain.repository.ILogbookRepository
import com.lans.sleep_care.domain.usecase.logbook.GetSleepDiariesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetSleepDiariesInteractor @Inject constructor(
    private val repository: ILogbookRepository
) : GetSleepDiariesUseCase, SafeApiCall {
    override suspend fun execute(therapyId: Int): Flow<Resource<List<SleepDiary>>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.fetchSleepDiaries(SleepDiaryRequest(therapyId))
                    response.data?.sleepDiaries?.values?.map {
                        it.map { diary -> diary.toDomain() }
                    }?.flatten() ?: throw Exception()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}