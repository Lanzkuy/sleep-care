package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.auth.LoginRequest
import com.lans.sleep_care.data.source.network.dto.response.toDomain
import com.lans.sleep_care.domain.model.Session
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val repository: IAuthRepository
) : LoginUseCase, SafeApiCall {
    override suspend fun execute(email: String, password: String): Flow<Resource<Session>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = repository.fetchLogin(
                    LoginRequest(email = email, password = password)
                ).data
                response?.toDomain() ?: throw Exception()
            })
        }.flowOn(Dispatchers.IO)
    }
}