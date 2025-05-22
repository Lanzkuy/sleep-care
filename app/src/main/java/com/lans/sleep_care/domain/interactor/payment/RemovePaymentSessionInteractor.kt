package com.lans.sleep_care.domain.interactor.payment

import com.lans.sleep_care.domain.repository.IPaymentRepository
import com.lans.sleep_care.domain.usecase.payment.RemovePaymentSessionUseCase
import javax.inject.Inject

class RemovePaymentSessionInteractor @Inject constructor(
    private val repository: IPaymentRepository
): RemovePaymentSessionUseCase {
    override suspend fun execute() {
        repository.deletePaymentSession()
    }
}