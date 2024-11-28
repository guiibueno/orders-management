package com.bueno.orders.application.dto.response

import java.math.BigInteger

data class CreateOrderResponse(val id: BigInteger,
                               val status: String) {
}