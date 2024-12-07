package com.bueno.orders.application.port.output

import com.bueno.orders.domain.entity.Order
import com.bueno.orders.domain.valueobject.OrderStatus
import java.math.BigInteger

interface OrderOutputPort {
    fun save(order: Order): Order
    fun getById(id: BigInteger): Order?
    fun getAll(status: OrderStatus?) : List<Order>
}