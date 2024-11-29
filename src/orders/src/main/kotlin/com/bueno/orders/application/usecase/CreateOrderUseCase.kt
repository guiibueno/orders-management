package com.bueno.orders.application.usecase

import com.bueno.orders.application.dto.request.CreateOrderRequest
import com.bueno.orders.application.dto.response.CreateOrderResponse
import com.bueno.orders.application.mapper.OrderMapper
import com.bueno.orders.application.port.input.CreateOrderPort
import com.bueno.orders.application.port.output.EventBusOutputPort
import com.bueno.orders.application.port.output.OrderOutputPort
import com.bueno.orders.domain.event.CreatedOrderEvent
import org.springframework.stereotype.Service

@Service
class CreateOrderUseCase (
    val orderOutputPort: OrderOutputPort,
    val orderMapper: OrderMapper,
    val eventBusOutputPort: EventBusOutputPort
) : CreateOrderPort {
    override fun invoke(request: CreateOrderRequest): CreateOrderResponse? {
        val entity = orderMapper.convertToDomain(request)
        val order = orderOutputPort.save(entity)

        eventBusOutputPort.send(CreatedOrderEvent(order))

        return CreateOrderResponse(order.id!!, order.status.toString())
    }
}