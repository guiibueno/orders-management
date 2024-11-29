package com.bueno.orders.domain.event

import java.time.LocalDateTime

interface Event <T> {
    val timestamp: LocalDateTime
    val data: T
}