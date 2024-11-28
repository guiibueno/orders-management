package com.bueno.orders.application.dto.response

import com.bueno.orders.domain.valueobject.OrderStatus
import java.math.BigInteger

data class OrderListItem(val id: BigInteger,
                         val status: OrderStatus,
                         val customerId: BigInteger) {
}