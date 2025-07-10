package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.auth.RegisterRequest
import com.lans.sleep_care.data.source.network.dto.response.user.toDomain
import com.lans.sleep_care.domain.model.auth.User
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterInteractor @Inject constructor(
    private val repository: IAuthRepository
) : RegisterUseCase, SafeApiCall {
    override suspend fun execute(
        user: User,
        password: String,
        passwordConfirmation: String
    ): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = repository.fetchRegister(
                    RegisterRequest(
                        name = user.name,
                        email = user.email,
                        password = password,
                        passwordConfirmation = passwordConfirmation,
                        age = user.age,
                        gender = user.gender.lowercase(),
                        problems = user.problems
                    )
                ).data
                response?.user?.toDomain() ?: throw Exception()
            })
        }.flowOn(Dispatchers.IO)
    }
}