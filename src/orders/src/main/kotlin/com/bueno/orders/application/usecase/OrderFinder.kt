package com.bueno.orders.application.usecase

import com.bueno.orders.application.dto.response.OrderDto
import com.bueno.orders.application.dto.response.OrderListItem
import com.bueno.orders.application.mapper.OrderMapper
import com.bueno.orders.application.port.input.OrderFinderPort
import com.bueno.orders.application.port.output.OrderOutputPort
import com.bueno.orders.domain.valueobject.OrderStatus
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class OrderFinder (
    val orderOutputPort: OrderOutputPort,
    val orderMapper: OrderMapper
) : OrderFinderPort {
    override fun findById(id: BigInteger): OrderDto? {
        val order = orderOutputPort.getById(id) ?: return null

        return orderMapper.convertToDto(order)
    }

    override fun findAll(status: OrderStatus?): List<OrderListItem> {
        val orders = orderOutputPort.getAll(status)

        return orders.map {
            OrderListItem(it.id!!, it.status, it.customerId)
        }
    }
}