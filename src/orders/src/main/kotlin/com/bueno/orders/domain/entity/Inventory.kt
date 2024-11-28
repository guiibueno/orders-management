package com.bueno.orders.domain.entity

import com.bueno.orders.domain.valueobject.InventoryStatus

data class Inventory (val status: InventoryStatus) {
    init {
        validate()
    }

    private fun validate() {
        require(status in InventoryStatus.entries.toTypedArray()) { "Status de inventário inválido: $status" }
    }
}