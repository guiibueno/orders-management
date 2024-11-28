package com.bueno.orders.domain.entity

import com.bueno.orders.domain.valueobject.InventoryStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class InventoryTest {

    // Teste para inventário com status válido
    @Test
    fun `should create inventory with valid status`() {
        val inventory = Inventory(status = InventoryStatus.RESERVED)

        // Verificando que o status do inventário foi atribuído corretamente
        assertEquals(InventoryStatus.RESERVED, inventory.status)
    }

    // Teste para inventário com status inválido (caso tenha sido alterado para algo fora da enumeração)
    @Test
    fun `should throw exception when status is invalid`() {
        val invalidStatus = "INVALID_STATUS" // Um valor de status que não existe na enumeração

        assertThrows<IllegalArgumentException> {
            Inventory(status = InventoryStatus.valueOf(invalidStatus))
        }
    }
}
