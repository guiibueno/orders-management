package com.bueno.orders.infraestructure.adapters.output.event

import com.bueno.orders.application.usecase.CreateOrderUseCase
import com.bueno.orders.domain.entity.Address
import com.bueno.orders.domain.entity.Delivery
import com.bueno.orders.domain.entity.Order
import com.bueno.orders.domain.entity.OrderItem
import com.bueno.orders.domain.event.CreatedOrderEvent
import com.bueno.orders.domain.event.Event
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import java.math.BigInteger
import java.util.concurrent.CompletableFuture
import kotlin.test.Test

class KafkaEventsProducerTest {

    private val orderEventsTopic = "orders-events-topic"

    @MockK
    private lateinit var kafkaOrdersEventTemplate: KafkaTemplate<String, Event<Order>>
    private var producer: KafkaEventsProducer

    init {
        MockKAnnotations.init(this)
        producer = spyk(
            KafkaEventsProducer(orderEventsTopic, kafkaOrdersEventTemplate)
        )

        every { kafkaOrdersEventTemplate.send(orderEventsTopic, any()) } returns CompletableFuture<SendResult<String, Event<Order>>>()
    }

    @Test
    fun `should send the CreatedOrderEvent event successfully`() {
        val order = Order(
            customerId = BigInteger("1"),
            items = listOf(OrderItem(id = BigInteger("1"), quantity = 2)),
            delivery = Delivery(address = Address(
                    street = "Rua Teste",
                    number = "123",
                    city = "Cidade Exemplo",
                    state = "SP",
                    country = "Brasil",
                    zipCode = "12345-678"
                )
            )
        )
        val createdOrderEvent = CreatedOrderEvent(order)

        producer.send(createdOrderEvent)

        coVerify (atLeast = 1, timeout = 300) {
            kafkaOrdersEventTemplate.send(orderEventsTopic, any())
        }
    }
}