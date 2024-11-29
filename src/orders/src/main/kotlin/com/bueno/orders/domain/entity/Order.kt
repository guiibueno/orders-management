package com.bueno.orders.domain.entity

import com.bueno.orders.domain.valueobject.InventoryStatus
import com.bueno.orders.domain.valueobject.OrderStatus
import com.bueno.orders.domain.valueobject.PaymentStatus
import org.springframework.data.annotation.Id
import java.math.BigInteger

data class Order(@Id val id: BigInteger?,
                 val customerId: BigInteger,
                 val status: OrderStatus,
                 val payment: Payment,
                 val inventory: Inventory,
                 val items: List<OrderItem>,
                 val delivery: Delivery) {

    constructor(customerId: BigInteger, items: List<OrderItem>, delivery: Delivery) : this (null, customerId, OrderStatus.PENDING, Payment(PaymentStatus.PENDING), Inventory(InventoryStatus.PENDING), items, delivery)

    init {
        validate()
    }

    private fun validate() {
        require(status in OrderStatus.entries.toTypedArray()) { "Status de pedido inválido: $status" }

        require(items.isNotEmpty()) { "O pedido deve ter pelo menos um item." }
    }
}