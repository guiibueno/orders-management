package com.bueno.orders.application.dto.response

import java.math.BigInteger

data class CancelOrderResponse(
    val id: BigInteger,
    val status: CancelOrderStatus,
    val reason: String
)

enum class CancelOrderStatus {
    CANCELED,
    ERROR
}