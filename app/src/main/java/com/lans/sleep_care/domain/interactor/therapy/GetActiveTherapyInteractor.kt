package com.lans.sleep_care.domain.interactor.therapy

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.response.therapy.toDomain
import com.lans.sleep_care.domain.model.therapy.Therapy
import com.lans.sleep_care.domain.repository.ITherapyRepository
import com.lans.sleep_care.domain.usecase.therapy.GetActiveTherapyUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetActiveTherapyInteractor @Inject constructor(
    private val repository: ITherapyRepository
) : GetActiveTherapyUseCase, SafeApiCall {
    override suspend fun execute(): Flow<Resource<Therapy?>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    repository.fetchTherapy().data?.toDomain()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}