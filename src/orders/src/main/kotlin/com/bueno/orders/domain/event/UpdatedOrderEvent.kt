package com.bueno.orders.domain.event

import java.math.BigInteger
import java.time.LocalDateTime

class UpdatedOrderEvent (
    override val timestamp: LocalDateTime,
    override val data: OrderUpdateDetailsDto
) : Event<OrderUpdateDetailsDto> {
    constructor(updateDetails: OrderUpdateDetailsDto) : this (LocalDateTime.now(), updateDetails)
}

data class OrderUpdateDetailsDto(
    val type: OrderUpdateType,
    val customerId: BigInteger,
    val orderId: BigInteger
)

enum class OrderUpdateType {
    CREATED,
    PAID,
    CANCELLED
}