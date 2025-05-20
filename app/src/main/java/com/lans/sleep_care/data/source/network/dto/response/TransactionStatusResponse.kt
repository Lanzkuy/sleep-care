package com.lans.sleep_care.data.source.network.dto.response

import com.squareup.moshi.Json

data class TransactionStatusResponse(
    @field:Json(name = "transaction_status")
    val transactionStatus: String,
    @field:Json(name = "status_code")
    val statusCode: String ,
    @field:Json(name = "fraud-Status")
    val fraudStatus: String
)
