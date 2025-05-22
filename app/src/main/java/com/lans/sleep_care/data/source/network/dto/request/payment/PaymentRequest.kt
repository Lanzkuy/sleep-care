package com.lans.sleep_care.data.source.network.dto.request.payment

import com.squareup.moshi.Json

data class PaymentRequest(
    @field:Json(name = "transaction_details")
    val transactionDetails: PaymentDetailRequest,
    @field:Json(name = "item_details")
    val itemDetails: List<ItemDetailRequest>,
    @field:Json(name = "customer_details")
    val customerDetails: CustomerDetailRequest
)

data class PaymentDetailRequest(
    @field:Json(name = "order_id")
    val orderId: String,
    @field:Json(name = "gross_amount")
    val grossAmount: Int
)

data class ItemDetailRequest(
    val price: Int,
    val quantity: Int,
    val name: String
)

data class CustomerDetailRequest(
    val email: String,
    @field:Json(name = "first_name")
    val firstName: String,
    @field:Json(name = "last_name")
    val lastName: String
)