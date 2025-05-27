package com.lans.sleep_care.domain.repository

import com.lans.sleep_care.data.source.network.dto.request.payment.CancelPaymentRequest
import com.lans.sleep_care.data.source.network.dto.request.payment.CreatePaymentRequest
import com.lans.sleep_care.data.source.network.dto.request.payment.UpdatePaymentRequest
import com.lans.sleep_care.data.source.network.dto.response.ApiResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.CancelPaymentResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.CheckPaymentResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.CreatePaymentResponse
import com.lans.sleep_care.data.source.network.dto.response.payment.UpdatePaymentResponse

interface IPaymentRepository {
    suspend fun checkMidtransPayment(orderId: String): CheckPaymentResponse

    suspend fun createMidtransPayment(request: CreatePaymentRequest): CreatePaymentResponse

    suspend fun updateMidtransPayment(request: UpdatePaymentRequest): ApiResponse<UpdatePaymentResponse>

    suspend fun cancelMidtransPayment(request: CancelPaymentRequest): ApiResponse<CancelPaymentResponse>
}