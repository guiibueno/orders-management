package com.bueno.orders.application.dto.response

import java.math.BigInteger

data class OrderDto (
    val id: BigInteger,
    val status: String,
    val payment: OrderPaymentDto,
    val inventory: OrderInventoryDto,
    val items: List<OrderItemDto>
)

data class OrderPaymentDto(
    val status: String
)

data class OrderInventoryDto(
    val status: String
)

data class OrderItemDto(
    val id: BigInteger,
    val quantity: Int
)