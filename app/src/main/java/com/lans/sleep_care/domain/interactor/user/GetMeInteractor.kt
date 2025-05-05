package com.lans.sleep_care.domain.interactor.user

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.response.toDomain
import com.lans.sleep_care.domain.model.User
import com.lans.sleep_care.domain.repository.IUserRepository
import com.lans.sleep_care.domain.usecase.user.GetMeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMeInteractor @Inject constructor(
    private val userRepository: IUserRepository
) : GetMeUseCase, SafeApiCall {
    override suspend fun execute(): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                userRepository.me().data.user.toDomain()
            })
        }.flowOn(Dispatchers.IO)
    }
}