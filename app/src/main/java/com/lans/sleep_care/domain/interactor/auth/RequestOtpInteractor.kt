package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.OtpRequest
import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.RequestOtpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RequestOtpInteractor @Inject constructor(
    private val repository: IAuthRepository
): RequestOtpUseCase, SafeApiCall {
    override suspend fun execute(request: OtpRequest): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading)
            emit(safeCall {
                val response = repository.sendOtp(request).message
                response == "Kode OTP berhasil dikirim."
            })
        }.flowOn(Dispatchers.IO)
    }
}