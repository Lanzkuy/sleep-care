package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.repository.IUserRepository
import com.lans.sleep_care.domain.usecase.auth.CheckAuthStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CheckAuthStatusInteractor @Inject constructor(
    private val authRepository: IAuthRepository,
    private val userRepository: IUserRepository
) : CheckAuthStatusUseCase, SafeApiCall {
    override suspend fun execute(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)

            authRepository.authState().collect { session ->
                if (!session) {
                    emit(Resource.Success(false))
                    return@collect
                }

                val result = safeCall {
                    val user = userRepository.fetchProfile().data?.user
                    val isUserValid = user != null
                    if (!isUserValid) {
                        authRepository.deleteToken()
                    }
                    isUserValid
                }

                emit(result)
            }
        }.flowOn(Dispatchers.IO)
    }
}