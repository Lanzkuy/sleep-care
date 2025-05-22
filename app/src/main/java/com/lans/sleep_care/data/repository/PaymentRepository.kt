package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.local.DataStoreManager
import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.payment.PaymentRequest
import com.lans.sleep_care.data.source.network.dto.response.payment.PaymentResponse
import com.lans.sleep_care.data.source.network.dto.response.therapy.OrderTherapyResponse
import com.lans.sleep_care.domain.repository.IPaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val api: SleepCareApi,
    private val dataStoreManager: DataStoreManager
) : IPaymentRepository {
    override suspend fun createPaymentCharge(request: PaymentRequest): PaymentResponse {
        return api.sendMidtransCharge(request)
    }

    override suspend fun getPaymentSession(): Flow<Triple<String, String, Int>> {
        return combine(
            dataStoreManager.getPaymentToken(),
            dataStoreManager.getOrderId(),
            dataStoreManager.getPsychologistId()
        ) { token, orderId, psychologistId ->
            Triple(token, orderId, psychologistId)
        }
    }

    override suspend fun savePaymentSession(token: String, orderId: String, psychologistId: Int) {
        dataStoreManager.storeData(key = DataStoreManager.PAYMENT_TOKEN, value = token)
        dataStoreManager.storeData(key = DataStoreManager.ORDER_ID, value = orderId)
        dataStoreManager.storeData(key = DataStoreManager.PSYCHOLOGIST_ID, value = psychologistId)
    }

    override suspend fun deletePaymentSession() {
        dataStoreManager.deletePaymentToken()
        dataStoreManager.deleteOrderId()
        dataStoreManager.deletePsychologistId()
    }
}