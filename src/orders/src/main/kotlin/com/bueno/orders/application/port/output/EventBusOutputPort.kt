package com.bueno.orders.application.port.output

import com.bueno.orders.domain.event.Event

interface EventBusOutputPort {
    fun <T> send(event: Event<T>)
}