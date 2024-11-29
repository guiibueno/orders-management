package com.bueno.orders.infraestructure.adapters.output.event

import com.bueno.orders.application.port.output.EventBusOutputPort
import com.bueno.orders.domain.entity.Order
import com.bueno.orders.domain.event.CreatedOrderEvent
import com.bueno.orders.domain.event.Event
import com.bueno.orders.infraestructure.adapters.output.event.KafkaEventsProducer.EventAsyncDispatcher.publishEventAsync
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
class KafkaEventsProducer (
    @Value("\${topics.order.events.name}")
    private val orderEventsTopic: String,
    private val kafkaOrdersEventTemplate: KafkaTemplate<String, Event<Order>>
) : EventBusOutputPort {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun <T> send(event: Event<T>) {
        publishEventAsync {
            try {
                when(event){
                    is CreatedOrderEvent -> send(event)
                    else -> logger.error("No event producer for type ${event.javaClass} was founded!")
                }

                logger.info("New event sent successfully.")
            }
            catch (ex: Exception){
                logger.error("Unable to send event.", ex)
            }
        }
    }

    fun send (event: CreatedOrderEvent){
        kafkaOrdersEventTemplate.send(orderEventsTopic, event)
    }

    object EventAsyncDispatcher {
        private val executor = Executors
            .newFixedThreadPool(1) { target -> Thread(target, "asynceventsthread") }
            .asCoroutineDispatcher()

        private val scope = CoroutineScope(executor)

        private val flow = MutableSharedFlow<() -> Unit>(
            replay = 1,
            extraBufferCapacity = 100,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

        init {
            flow
                .onEach { it() }
                .launchIn(scope)
        }

        fun publishEventAsync(callable: () -> Unit){
            flow.tryEmit(callable)
        }
    }
}