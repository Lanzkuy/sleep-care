package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.auth.PasswordResetRequest
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.ResetPasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ResetPasswordInteractor @Inject constructor(
    private val repository: IAuthRepository
) : ResetPasswordUseCase, SafeApiCall {
    override suspend fun execute(
        email: String,
        token: Int,
        password: String,
        passwordConfirmation: String
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.resetPassword(
                        PasswordResetRequest(
                            email = email,
                            token = token,
                            password = password,
                            passwordConfirmation = passwordConfirmation
                        )
                    ).message
                    response == "Password berhasil direset."
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}