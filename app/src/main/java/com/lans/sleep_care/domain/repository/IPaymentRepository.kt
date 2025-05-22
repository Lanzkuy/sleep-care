package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.payment.PaymentRequest
import com.lans.sleep_care.data.source.network.dto.response.payment.PaymentResponse
import kotlinx.coroutines.flow.Flow

interface IPaymentRepository {
    suspend fun createPaymentCharge(request: PaymentRequest): PaymentResponse

    suspend fun getPaymentSession(): Flow<Triple<String, String, Int>>

    suspend fun savePaymentSession(token: String, orderId: String, psychologistId: Int)

    suspend fun deletePaymentSession()
}