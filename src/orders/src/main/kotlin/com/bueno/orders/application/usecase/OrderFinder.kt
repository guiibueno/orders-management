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
        TODO("Not yet implemented")
    }

    override fun findAll(status: OrderStatus): List<OrderListItem> {
        TODO("Not yet implemented")
    }
}