package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.VerifyOtpRequest
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.VerifyOtpUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyOtpInteractor @Inject constructor(
    private val authRepository: IAuthRepository
): VerifyOtpUseCase, SafeApiCall {
    override suspend fun execute(request: VerifyOtpRequest): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = authRepository.verifyOtp(request).message
                response == "Kode OTP berhasil diverifikasi."
            })
        }
    }
}