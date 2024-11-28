package com.bueno.orders.application.port.input

import com.bueno.orders.application.dto.response.OrderDto
import com.bueno.orders.application.dto.response.OrderListItem
import com.bueno.orders.domain.valueobject.OrderStatus
import java.math.BigInteger

interface OrderFinderPort {
    fun findById(id: BigInteger): OrderDto?
    fun findAll(status: OrderStatus): List<OrderListItem>
}