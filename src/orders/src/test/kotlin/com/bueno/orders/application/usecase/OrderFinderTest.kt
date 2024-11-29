package com.bueno.orders.application.usecase

import com.bueno.orders.application.mapper.OrderMapper
import com.bueno.orders.application.port.output.OrderOutputPort
import com.bueno.orders.domain.entity.*
import com.bueno.orders.domain.valueobject.InventoryStatus
import com.bueno.orders.domain.valueobject.OrderStatus
import com.bueno.orders.domain.valueobject.PaymentStatus
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class OrderFinderTest {
    private val orderMapper = OrderMapper()

    @MockK
    private lateinit var orderOutputPort: OrderOutputPort

    private var orderFinder: OrderFinder

    init {
        MockKAnnotations.init(this)
        orderFinder = spyk(
            OrderFinder(orderOutputPort, orderMapper)
        )
    }

    @Test
    fun `should return data when order is found`() {
        val orderId = BigInteger("1")
        val order = Order(id = BigInteger("1"),
            customerId = BigInteger("1"),
            status = OrderStatus.PENDING,
            payment = Payment(PaymentStatus.PENDING),
            inventory = Inventory(InventoryStatus.PENDING),
            items = listOf(OrderItem(id = BigInteger("1"), quantity = 2)),
            delivery = Delivery(
                Address(
                    street = "Rua Teste",
                    number = "123",
                    city = "Cidade Exemplo",
                    state = "SP",
                    country = "Brasil",
                    zipCode = "12345-678"
                )
            )
        )

        every { orderOutputPort.getById(any()) } returns order

        val result = orderFinder.findById(orderId)

        assertNotNull(result)
        coVerify (exactly = 1) {
            orderOutputPort.getById(any())
        }
    }

    @Test
    fun `should return null when order is not found`() {
        val orderId = BigInteger("1")

        every { orderOutputPort.getById(orderId) } returns null

        val result = orderFinder.findById(orderId)

        assertNull(result)

        coVerify (exactly = 1) {
            orderOutputPort.getById(any())
        }
    }
}
