package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.auth.OtpVerifyRequest
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.VerifyOtpUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyOtpInteractor @Inject constructor(
    private val repository: IAuthRepository
): VerifyOtpUseCase, SafeApiCall {
    override suspend fun execute(otp: String, email: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = repository.verifyOtp(
                    OtpVerifyRequest(otp = otp, email = email)
                ).message
                response == "Kode OTP berhasil diverifikasi."
            })
        }
    }
}