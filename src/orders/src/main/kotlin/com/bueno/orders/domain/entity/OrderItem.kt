package com.bueno.orders.domain.entity

import java.math.BigInteger

data class OrderItem(val id: BigInteger,
                     val quantity: Int) {

    init {
        validate()
    }

    private fun validate() {
        require(isValidQuantity)
    }

    private val isValidQuantity: Boolean
        get() = quantity > 0

}