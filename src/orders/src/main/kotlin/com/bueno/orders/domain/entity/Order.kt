package com.bueno.orders.domain.entity

import com.bueno.orders.domain.valueobject.OrderStatus
import com.bueno.orders.domain.valueobject.PaymentStatus
import org.springframework.data.annotation.Id
import java.math.BigInteger

data class Order(@Id val id: BigInteger?,
                 val status: OrderStatus,
                 val payment: Payment,
                 val items: List<OrderItem>,
                 val delivery: Delivery) {

    constructor(items: List<OrderItem>, delivery: Delivery) : this (null, OrderStatus.PENDING, Payment(PaymentStatus.PENDING), items, delivery)

    init {
        validate()
    }

    private fun validate() {
        require(status in OrderStatus.entries.toTypedArray()) { "Status de pedido inválido: $status" }

        require(items.isNotEmpty()) { "O pedido deve ter pelo menos um item." }
    }
}