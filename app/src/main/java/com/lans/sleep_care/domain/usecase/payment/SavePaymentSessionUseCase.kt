package com.lans.sleep_care.domain.usecase.payment

interface SavePaymentSessionUseCase {
    suspend fun invoke(paymentToken: String, orderId: String, psychologistId: Int)
}