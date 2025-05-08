package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.IsAuthenticatedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class IsAuthenticatedInteractor @Inject constructor(
    private val authRepository: IAuthRepository
) : IsAuthenticatedUseCase {
    override suspend fun execute(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            authRepository.authState().collect { result ->
                emit(Resource.Success(result))
            }
        }.flowOn(Dispatchers.IO)
    }
}