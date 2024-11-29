package com.bueno.orders.application.usecase

import com.bueno.orders.application.dto.request.CreateOrderRequest
import com.bueno.orders.application.dto.request.OrderDelivery
import com.bueno.orders.application.dto.request.OrderDeliveryAddress
import com.bueno.orders.application.dto.request.OrderItems
import com.bueno.orders.application.mapper.OrderMapper
import com.bueno.orders.application.port.output.OrderOutputPort
import com.bueno.orders.domain.entity.Delivery
import com.bueno.orders.domain.entity.Inventory
import com.bueno.orders.domain.entity.Order
import com.bueno.orders.domain.entity.Payment
import com.bueno.orders.domain.valueobject.InventoryStatus
import com.bueno.orders.domain.valueobject.OrderStatus
import com.bueno.orders.domain.valueobject.PaymentStatus
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CreateOrderUseCaseTest {

    private val orderMapper = OrderMapper()

    @MockK
    private lateinit var orderOutputPort: OrderOutputPort

    private var createOrderUseCase: CreateOrderUseCase

    init {
        MockKAnnotations.init(this)
        createOrderUseCase = spyk(
            CreateOrderUseCase(orderOutputPort, orderMapper)
        )
    }

    @Test
    fun `should create order successfully`() {
        val request = createMockCreateOrderRequest()
        val order = Order(id = BigInteger("1"),
            customerId = BigInteger("1"),
            status = OrderStatus.PENDING,
            payment = Payment(PaymentStatus.PENDING),
            inventory = Inventory(InventoryStatus.PENDING),
            items = orderMapper.convertToDomain(request.items),
            delivery = Delivery(orderMapper.convertToDomain(request.delivery.address)))

        every { orderOutputPort.save(any()) } returns order

        val response = createOrderUseCase.invoke(request)

        assertNotNull(response)
        assertEquals(order.id!!, response.id)
        assertEquals(order.status.toString(), response.status)

        coVerify (exactly = 1) {
            orderOutputPort.save(any())
        }
    }

    @Test
    fun `should throw exception when saving order fails`() {
        val request = createMockCreateOrderRequest()

        every { orderOutputPort.save(any()) } throws RuntimeException("Erro ao salvar pedido")

        assertThrows<RuntimeException> {
            createOrderUseCase.invoke(request)
        }

        coVerify (exactly = 1) {
            orderOutputPort.save(any())
        }
    }

    private fun createMockCreateOrderRequest(): CreateOrderRequest {
        val orderItem1 = OrderItems(id = BigInteger("1"), quantity = 2)
        val orderItem2 = OrderItems(id = BigInteger("2"), quantity = 1)

        val orderDeliveryAddress = OrderDeliveryAddress(
            street = "Main St",
            number = "123",
            city = "São Paulo",
            state = "SP",
            country = "Brasil",
            zipCode = "00000000"
        )

        val orderDelivery = OrderDelivery(address = orderDeliveryAddress)

        return CreateOrderRequest(
            customerId = BigInteger("123456789"),
            items = listOf(orderItem1, orderItem2),
            delivery = orderDelivery
        )
    }
}
