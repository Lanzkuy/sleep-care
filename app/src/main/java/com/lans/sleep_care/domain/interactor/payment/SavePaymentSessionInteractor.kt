package com.lans.sleep_care.domain.interactor.payment

import com.lans.sleep_care.domain.repository.IPaymentRepository
import com.lans.sleep_care.domain.usecase.payment.SavePaymentSessionUseCase
import javax.inject.Inject

class SavePaymentSessionInteractor @Inject constructor(
    private val repository: IPaymentRepository
): SavePaymentSessionUseCase {
    override suspend fun invoke(paymentToken: String, orderId: String, psychologistId: Int) {
        repository.savePaymentSession(paymentToken, orderId, psychologistId)
    }
}