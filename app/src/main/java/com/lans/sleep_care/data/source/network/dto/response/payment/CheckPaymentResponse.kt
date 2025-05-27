package com.lans.sleep_care.data.source.network.dto.response.payment

import com.squareup.moshi.Json

data class CheckPaymentResponse(
    @field:Json(name = "status_code")
    val statusCode: String,
    @field:Json(name = "transaction_id")
    val transactionId: String,
    @field:Json(name = "gross_amount")
    val grossAmount: String,
    @field:Json(name = "currency")
    val currency: String,
    @field:Json(name = "order_id")
    val orderId: String,
    @field:Json(name = "payment_type")
    val paymentType: String,
    @field:Json(name = "signature_key")
    val signatureKey: String,
    @field:Json(name = "transaction_status")
    val transactionStatus: String,
    @field:Json(name = "fraud_status")
    val fraudStatus: String,
    @field:Json(name = "status_message")
    val statusMessage: String,
    @field:Json(name = "merchant_id")
    val merchantId: String,
    @field:Json(name = "va_numbers")
    val vaNumbers: List<VaNumber>,
    @field:Json(name = "payment_amounts")
    val paymentAmounts: List<Any>,
    @field:Json(name = "transaction_time")
    val transactionTime: String,
    @field:Json(name = "expiry_time")
    val expiryTime: String
)

data class VaNumber(
    @field:Json(name = "bank")
    val bank: String,
    @field:Json(name = "va_number")
    val vaNumber: String
)