package com.bueno.orders.domain.entity

import com.bueno.orders.domain.valueobject.PaymentStatus

data class Payment (val status: PaymentStatus) {
    init {
        validate()
    }

    private fun validate() {
        require(status in PaymentStatus.entries.toTypedArray()) { "Status de pagamento inválido: $status" }
    }
}