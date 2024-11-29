package com.bueno.orders.application.mapper

import com.bueno.orders.application.dto.request.CreateOrderRequest
import com.bueno.orders.application.dto.request.OrderDelivery
import com.bueno.orders.application.dto.request.OrderDeliveryAddress
import com.bueno.orders.application.dto.request.OrderItems
import com.bueno.orders.application.dto.response.OrderDto
import com.bueno.orders.application.dto.response.OrderInventoryDto
import com.bueno.orders.application.dto.response.OrderItemDto
import com.bueno.orders.application.dto.response.OrderPaymentDto
import com.bueno.orders.domain.entity.*
import org.springframework.stereotype.Component

@Component
class OrderMapper {
    fun convertToDomain(dto: CreateOrderRequest) : Order {
        val address = convertToDomain(dto.delivery.address)
        val items = convertToDomain(dto.items)

        return Order(items, Delivery(address))
    }

    fun convertToDomain(dto: OrderDeliveryAddress) : Address {
        return Address(dto.street, dto.number, dto.city, dto.state, dto.country, dto.zipCode)
    }

    fun convertToDomain(dto: List<OrderItems>) : List<OrderItem> {
        return dto.map {
            OrderItem(it.id, it.quantity)
        }
    }

    fun convertToDto(entity: Order) : OrderDto{
        val payment = convertToDto(entity.payment)
        val inventory = convertToDto(entity.inventory)
        val items = convertToDto(entity.items)

        return OrderDto(entity.id!!, entity.status.toString(), payment, inventory, items)
    }

    fun convertToDto(entity: Payment) : OrderPaymentDto {
        return OrderPaymentDto(entity.status.toString())
    }

    fun convertToDto(entity: Inventory) : OrderInventoryDto {
        return OrderInventoryDto(entity.status.toString())
    }

    fun convertToDto(entity: List<OrderItem>) : List<OrderItemDto> {
        return entity.map {
            OrderItemDto(it.id, it.quantity)
        }
    }
}