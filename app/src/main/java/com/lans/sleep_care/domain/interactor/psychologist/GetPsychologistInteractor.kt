package com.lans.sleep_care.domain.interactor.psychologist

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.response.psychologist.toDomain
import com.lans.sleep_care.domain.model.Psychologist
import com.lans.sleep_care.domain.repository.IPsychologistRepository
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPsychologistInteractor @Inject constructor(
    private val repository: IPsychologistRepository
) : GetPsychologistUseCase, SafeApiCall {
    override suspend fun execute(id: Int): Flow<Resource<Psychologist>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = repository.fetchPsychologist(id).data
                response?.toDomain() ?: throw Exception()
            })
        }.flowOn(Dispatchers.IO)
    }
}