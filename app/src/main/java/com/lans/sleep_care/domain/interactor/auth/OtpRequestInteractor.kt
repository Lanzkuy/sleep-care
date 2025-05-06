package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.OtpRequest
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.OtpRequestUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OtpRequestInteractor @Inject constructor(
    private val authRepository: IAuthRepository
): OtpRequestUseCase, SafeApiCall {
    override suspend fun execute(request: OtpRequest): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = authRepository.requestOtp(request).message
                response == "Kode OTP berhasil dikirim."
            })
        }
    }
}