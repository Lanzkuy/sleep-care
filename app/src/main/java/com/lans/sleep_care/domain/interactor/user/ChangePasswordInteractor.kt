package com.lans.sleep_care.domain.interactor.user

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.user.PasswordChangeRequest
import com.lans.sleep_care.domain.repository.IUserRepository
import com.lans.sleep_care.domain.usecase.user.ChangePasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChangePasswordInteractor @Inject constructor(
    private val repository: IUserRepository
) : ChangePasswordUseCase, SafeApiCall {
    override suspend fun execute(
        currentPassword: String,
        newPassword: String,
        newPasswordConfirmation: String
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.changePassword(
                        PasswordChangeRequest(
                            currentPassword = currentPassword,
                            newPassword = newPassword,
                            newPasswordConfirmation = newPasswordConfirmation
                        )
                    ).message
                    response == "Password berhasil diubah."
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}