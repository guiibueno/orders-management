package com.bueno.orders.domain.entity

data class Delivery(val address: Address) {
    init {
        validate()
    }

    private fun validate() {

    }
}