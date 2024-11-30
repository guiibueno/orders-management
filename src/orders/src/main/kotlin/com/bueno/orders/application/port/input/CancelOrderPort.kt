package com.bueno.orders.application.port.input

import com.bueno.orders.application.dto.response.CancelOrderResponse
import java.math.BigInteger

interface CancelOrderPort {
    fun invoke(id: BigInteger): CancelOrderResponse?
}