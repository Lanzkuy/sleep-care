package com.lans.sleep_care.domain.interactor.user

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.response.user.toDomain
import com.lans.sleep_care.domain.model.User
import com.lans.sleep_care.domain.repository.IUserRepository
import com.lans.sleep_care.domain.usecase.user.GetUserProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserProfileInteractor @Inject constructor(
    private val userRepository: IUserRepository
) : GetUserProfileUseCase, SafeApiCall {
    override suspend fun execute(): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = userRepository.fetchProfile().data
                response?.toDomain() ?: throw Exception()
            })
        }.flowOn(Dispatchers.IO)
    }
}