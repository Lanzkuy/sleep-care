package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.response.therapy.toDomain
import com.lans.sleep_care.domain.model.therapy.Therapy
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.GetCompletedTherapyUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCompletedTherapyInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : GetCompletedTherapyUseCase, SafeApiCall {
    override suspend fun execute(): Flow<Resource<List<Therapy>>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.fetchCompletedTherapy().data
                    response?.therapies?.map { it.toDomain() } ?: throw Exception()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}