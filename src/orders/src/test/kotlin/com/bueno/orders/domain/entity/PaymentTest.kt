package com.bueno.orders.domain.entity

import com.bueno.orders.domain.valueobject.PaymentStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class PaymentTest {

    // Teste para pagamento com status válido
    @Test
    fun `should create payment with valid status`() {
        val payment = Payment(status = PaymentStatus.APPROVED)

        // Verificando que o status do pagamento foi atribuído corretamente
        assertEquals(PaymentStatus.APPROVED, payment.status)
    }

    // Teste para status inválido (valor não existente na enumeração)
    @Test
    fun `should throw exception when status is invalid`() {
        val invalidStatus = "INVALID_STATUS" // Valor não existente na enumeração

        assertThrows<IllegalArgumentException> {
            Payment(status = PaymentStatus.valueOf(invalidStatus))
        }
    }
}
