package com.lans.sleep_care.domain.interactor.user

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.user.ProfileUpdateRequest
import com.lans.sleep_care.domain.model.User
import com.lans.sleep_care.domain.repository.IUserRepository
import com.lans.sleep_care.domain.usecase.user.UpdateProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateProfileInteractor @Inject constructor(
    private val repository: IUserRepository
) : UpdateProfileUseCase, SafeApiCall {
    override suspend fun execute(user: User): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.updateProfile(
                        ProfileUpdateRequest(
                            id = user.id,
                            name = user.name,
                            age = user.age,
                            gender = user.gender,
                            problems = user.problems
                        )
                    ).message
                    response == "Berhasil mengubah data profile."
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}