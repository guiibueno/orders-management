package com.bueno.orders.domain.entity

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DeliveryTest {

    // Teste para uma entrega válida
    @Test
    fun `should create delivery with valid address and fields`() {
        val address = Address(
            street = "Rua Teste",
            number = "123",
            city = "Cidade Exemplo",
            state = "SP",
            country = "Brasil",
            zipCode = "12345-678"
        )

        val delivery = Delivery(
            address = address
        )

        // Verificando que a entrega foi criada com sucesso
        assertEquals("Rua Teste", delivery.address.street)
        assertEquals("123", delivery.address.number)
    }
}
