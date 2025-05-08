package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.StoreSessionUseCase
import javax.inject.Inject

class StoreSessionInteractor @Inject constructor(
    private val authRepository: IAuthRepository
): StoreSessionUseCase {
    override suspend fun invoke(accessToken: String) {
        authRepository.saveToken(accessToken)
    }
}