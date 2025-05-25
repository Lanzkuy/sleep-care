package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.response.therapy.toDomain
import com.lans.sleep_care.domain.model.therapy.TherapySchedule
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.GetTherapySchedulesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTherapyScheduleInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : GetTherapySchedulesUseCase, SafeApiCall {
    override suspend fun execute(therapyId: Int): Flow<Resource<List<TherapySchedule>>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.fetchTherapySchedules(therapyId).data
                    response?.schedules?.map { it.toDomain() } ?: throw Exception()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}