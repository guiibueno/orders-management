package com.bueno.orders.application.port.input

import com.bueno.orders.application.dto.request.CreateOrderRequest
import com.bueno.orders.application.dto.response.CreateOrderResponse

interface CreateOrderPort {
    fun invoke(request: CreateOrderRequest): CreateOrderResponse?
}