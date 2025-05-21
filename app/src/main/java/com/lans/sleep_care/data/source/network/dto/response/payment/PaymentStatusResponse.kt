package com.lans.sleep_care.data.source.network.dto.response.payment

import com.squareup.moshi.Json

data class PaymentStatusResponse(
    @field:Json(name = "transaction_status")
    val transactionStatus: String,
    @field:Json(name = "status_code")
    val statusCode: String ,
    @field:Json(name = "fraud-Status")
    val fraudStatus: String
)
