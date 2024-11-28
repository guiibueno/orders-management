package com.bueno.orders.domain.entity

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import kotlin.test.assertEquals

class OrderItemTest {

    // Teste para criação de OrderItem com quantidade válida
    @Test
    fun `should create order item with valid quantity`() {
        val orderItem = OrderItem(id = BigInteger("1"), quantity = 5)

        // Verificando que o item foi criado corretamente
        assertEquals(BigInteger("1"), orderItem.id)
        assertEquals(5, orderItem.quantity)
    }

    // Teste para quantidade inválida (quantidade <= 0)
    @Test
    fun `should throw exception when quantity is invalid`() {
        // Quantidade negativa
        assertThrows<IllegalArgumentException> {
            OrderItem(id = BigInteger("1"), quantity = -1)
        }

        // Quantidade 0
        assertThrows<IllegalArgumentException> {
            OrderItem(id = BigInteger("2"), quantity = 0)
        }
    }
}
