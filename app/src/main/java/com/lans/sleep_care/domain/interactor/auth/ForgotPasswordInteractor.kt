package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.ForgotPasswordRequest
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.ForgotPasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ForgotPasswordInteractor @Inject constructor(
    private val repository: IAuthRepository
): ForgotPasswordUseCase, SafeApiCall {
    override suspend fun execute(email: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val response = repository.forgotPassword(
                        ForgotPasswordRequest(email)
                    ).message
                    response == "Kode OTP berhasil dikirimkan."
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}