package com.bueno.orders.application.mapper

import com.bueno.orders.application.dto.request.CreateOrderRequest
import com.bueno.orders.application.dto.request.OrderDeliveryAddress
import com.bueno.orders.application.dto.request.OrderItems
import com.bueno.orders.domain.entity.Address
import com.bueno.orders.domain.entity.Delivery
import com.bueno.orders.domain.entity.Order
import com.bueno.orders.domain.entity.OrderItem
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
}