package com.lans.sleep_care.domain.interactor

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.response.toDomain
import com.lans.sleep_care.domain.model.User
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterInteractor @Inject constructor(
    private val repository: IAuthRepository
): RegisterUseCase, SafeApiCall {
    override suspend fun execute(request: RegisterRequest): Flow<Resource<User>> {
        return flow{
            emit(Resource.Loading)
            emit(safeCall {
                repository.register(request).data.user.toDomain()
            })
        }.flowOn(Dispatchers.IO)
    }
}