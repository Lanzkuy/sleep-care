package com.lans.sleep_care.data.repository

import com.lans.sleep_care.data.source.network.api.SleepCareApi
import com.lans.sleep_care.data.source.network.dto.request.payment.CancelPaymentRequest
import com.lans.sleep_care.data.source.network.dto.request.payment.CreatePaymentRequest
import com.lans.sleep_care.data.source.network.dto.request.payment.UpdatePaymentRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.CancelPaymentResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.CheckPaymentResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.CreatePaymentResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.UpdatePaymentResponse
import com.lans.sleep_care.domain.repository.IPaymentRepository
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val api: SleepCareApi
) : IPaymentRepository {
    override suspend fun checkMidtransPayment(orderId: String): CheckPaymentResponse {
        return api.checkMidtransPayment(orderId)
    }

    override suspend fun createMidtransPayment(request: CreatePaymentRequest): CreatePaymentResponse {
        return api.createMidtransPayment(request)
    }

    override suspend fun updateMidtransPayment(request: UpdatePaymentRequest): ApiResponse<UpdatePaymentResponse> {
        return api.updateMidtransPayment(request)
    }

    override suspend fun cancelMidtransPayment(request: CancelPaymentRequest): ApiResponse<CancelPaymentResponse> {
        return api.cancelMidtransPayment(request)
    }
}