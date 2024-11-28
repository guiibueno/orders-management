package com.bueno.orders.domain.entity

import com.bueno.orders.domain.valueobject.OrderStatus
import com.bueno.orders.domain.valueobject.PaymentStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import kotlin.test.assertEquals

class OrderTest {

    // Teste para criar um pedido com status válido e pagamento válido
    @Test
    fun `should create order with valid status and payment`() {
        val order = Order(
            items = listOf(OrderItem(id = BigInteger("1"), quantity = 2)),
            delivery = Delivery(address = Address(
                street = "Rua Teste",
                number = "123",
                city = "Cidade Exemplo",
                state = "SP",
                country = "Brasil",
                zipCode = "12345-678"
            ))
        )

        // Verificando os campos do pedido
        assertEquals(OrderStatus.PENDING, order.status)
        assertEquals(PaymentStatus.PENDING, order.payment.status)
    }

    // Teste para quando a lista de itens estiver vazia
    @Test
    fun `should throw exception when items list is empty`() {
        assertThrows<IllegalArgumentException> {
            Order(
                items = emptyList(),
                delivery = Delivery(address = Address(
                    street = "Rua Teste",
                    number = "123",
                    city = "Cidade Exemplo",
                    state = "SP",
                    country = "Brasil",
                    zipCode = "12345-678"
                ))
            )
        }
    }
}
