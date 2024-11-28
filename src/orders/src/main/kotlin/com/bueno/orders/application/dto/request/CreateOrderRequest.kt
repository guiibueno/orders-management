package com.bueno.orders.application.dto.request

import java.math.BigInteger

data class CreateOrderRequest (val customerId: BigInteger,
                               val items: List<OrderItems>,
                               val delivery: OrderDelivery)

data class OrderItems(val id: BigInteger, val quantity: Int)

data class OrderDelivery(val address: OrderDeliveryAddress)

data class OrderDeliveryAddress(val street: String,
                                val number: String,
                                val city: String,
                                val state: String,
                                val country: String,
                                val zipCode: String)