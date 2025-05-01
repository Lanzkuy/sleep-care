package com.lans.sleep_care.domain.interactor

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.LoginRequest
import com.lans.sleep_care.data.source.network.dto.response.toDomain
import com.lans.sleep_care.domain.model.User
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val repository: IAuthRepository
) : LoginUseCase, SafeApiCall {
    override suspend fun execute(loginRequest: LoginRequest): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                repository.login(loginRequest).data.toDomain()
            })
        }
    }
}