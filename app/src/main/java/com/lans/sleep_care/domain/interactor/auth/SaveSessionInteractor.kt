package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.SaveSessionUseCase
import javax.inject.Inject

class SaveSessionInteractor @Inject constructor(
    private val repository: IAuthRepository
): SaveSessionUseCase {
    override suspend fun invoke(accessToken: String) {
        repository.saveToken(accessToken)
    }
}