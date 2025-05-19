package com.lans.sleep_care.domain.interactor.psychologist

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.psychologist.PsychologistListRequest
import com.lans.sleep_care.data.source.network.dto.response.toDomain
import com.lans.sleep_care.domain.model.Psychologist
import com.lans.sleep_care.domain.repository.IPsychologistRepository
import com.lans.sleep_care.domain.usecase.psychologist.GetAllPsychologistUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllPsychologistInteractor @Inject constructor(
    private val repository: IPsychologistRepository
) : GetAllPsychologistUseCase, SafeApiCall {
    override suspend fun execute(page: Int): Flow<Resource<List<Psychologist>>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = repository.fetchAllPsychologist(
                    PsychologistListRequest(page = page)
                ).data
                response?.psychologists?.map { it.toDomain() } ?: throw Exception()
            })
        }.flowOn(Dispatchers.IO)
    }
}