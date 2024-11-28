package com.bueno.orders.application.usecase

import com.bueno.orders.application.dto.request.CreateOrderRequest
import com.bueno.orders.application.dto.response.CreateOrderResponse
import com.bueno.orders.application.mapper.OrderMapper
import com.bueno.orders.application.port.input.CreateOrderPort
import com.bueno.orders.application.port.output.OrderOutputPort
import org.springframework.stereotype.Service

@Service
class CreateOrderUseCase (
    val orderOutputPort: OrderOutputPort,
    val orderMapper: OrderMapper
) : CreateOrderPort {
    override fun invoke(request: CreateOrderRequest): CreateOrderResponse? {
        val entity = orderMapper.convertToDomain(request)
        val order = orderOutputPort.save(entity)

        return CreateOrderResponse(order.id!!, order.status.toString())
    }
}