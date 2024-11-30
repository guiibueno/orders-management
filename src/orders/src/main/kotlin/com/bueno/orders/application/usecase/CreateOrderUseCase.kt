package com.bueno.orders.application.usecase

import com.bueno.orders.application.dto.request.CreateOrderRequest
import com.bueno.orders.application.dto.response.CreateOrderResponse
import com.bueno.orders.application.mapper.OrderMapper
import com.bueno.orders.application.port.input.CreateOrderPort
import com.bueno.orders.application.port.output.EventBusOutputPort
import com.bueno.orders.application.port.output.OrderOutputPort
import com.bueno.orders.domain.entity.Order
import com.bueno.orders.domain.event.CreatedOrderEvent
import com.bueno.orders.domain.event.OrderUpdateDetailsDto
import com.bueno.orders.domain.event.OrderUpdateType
import com.bueno.orders.domain.event.UpdatedOrderEvent
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

        emitEvents(order)

        return CreateOrderResponse(order.id!!, order.status.toString())
    }

    private fun emitEvents(order: Order){
        emitCreatedOrderEvent(order)
        emitCreatedOrderNotificationEvent(order)
    }

    private fun emitCreatedOrderEvent(order: Order){
        val createdOrderEvent = CreatedOrderEvent(order)
        eventBusOutputPort.send(createdOrderEvent)
    }

    private fun emitCreatedOrderNotificationEvent(order: Order){
        val details = OrderUpdateDetailsDto(OrderUpdateType.CREATED, order.customerId, order.id!!)
        val notificationEvent = UpdatedOrderEvent(details)

        eventBusOutputPort.send(notificationEvent)
    }
}