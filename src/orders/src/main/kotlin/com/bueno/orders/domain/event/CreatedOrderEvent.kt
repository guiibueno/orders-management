package com.bueno.orders.domain.event

import com.bueno.orders.domain.entity.Order
import java.time.LocalDateTime

data class CreatedOrderEvent (
    override val timestamp: LocalDateTime,
    override val data: Order
) : Event<Order> {
    constructor(order: Order) : this (LocalDateTime.now(), order)
}