package com.lans.sleep_care.domain.interactor.auth

import com.lans.sleep_care.domain.repository.IAuthRepository
import com.lans.sleep_care.domain.usecase.auth.LogoutUseCase
import javax.inject.Inject

class LogoutInteractor @Inject constructor(
    private val repository: IAuthRepository
) : LogoutUseCase {
    override suspend fun execute() {
        repository.deleteToken()
    }
}