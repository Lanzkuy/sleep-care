package com.lans.sleep_care.domain.interactor.logbook

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.domain.repository.ILogbookRepository
import com.lans.sleep_care.domain.usecase.logbook.GetEmotionsUseCase
import com.lans.sleep_care.domain.usecase.logbook.GetProblemsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetProblemsInteractor @Inject constructor(
    private val repository: ILogbookRepository
) : GetProblemsUseCase, SafeApiCall {
    override suspend fun execute(): Flow<Resource<List<String>>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    repository.fetchProblems().data ?: throw Exception()
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}